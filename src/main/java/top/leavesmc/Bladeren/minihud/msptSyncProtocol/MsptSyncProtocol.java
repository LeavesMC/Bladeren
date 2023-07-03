package top.leavesmc.Bladeren.minihud.msptSyncProtocol;

import fi.dy.masa.minihud.util.DataStorage;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.config.Configs;
import top.leavesmc.Bladeren.mixin.accessor.AccessorDataStorage;

public class MsptSyncProtocol {

    private static final ResourceLocation MSPT_SYNC_ENABLE = ModInfo.id("mspt_sync_enable");
    private static final ResourceLocation MSPT_SYNC = ModInfo.id("mspt_sync");

    private static AccessorDataStorage dataStorage;

    private static boolean sendEnable = false;

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(MSPT_SYNC, MsptSyncProtocol::updateMspt);
        ClientPlayNetworking.registerGlobalReceiver(MSPT_SYNC_ENABLE, MsptSyncProtocol::enableMsptSync);
    }

    private static void enableMsptSync(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        if (Configs.msptSyncProtocol) {
            dataStorage = (AccessorDataStorage) DataStorage.getInstance();
            sendEnable = true;
        }
    }

    public static void onPlayerJoin() {
        if (sendEnable) {
            ClientPlayNetworking.send(MSPT_SYNC_ENABLE, new FriendlyByteBuf(Unpooled.buffer()));
            sendEnable = false;
        }
    }

    private static void updateMspt(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        if (Configs.msptSyncProtocol) {
            dataStorage.setServerMSPT(buf.readDouble());
            dataStorage.setServerTPS(buf.readDouble());
            dataStorage.setServerTPSValid(true);
            dataStorage.setCarpetServer(true);
        }
    }
}
