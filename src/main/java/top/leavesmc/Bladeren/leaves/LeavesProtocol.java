package top.leavesmc.Bladeren.leaves;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.event.DisconnectEvent;

public class LeavesProtocol {

    private static final ResourceLocation LAVA_RIPTIDE = ModInfo.id("lava_riptide");

    public static boolean lavaRiptideEnable = true;

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(LAVA_RIPTIDE, LeavesProtocol::lavaRiptide);
        DisconnectEvent.register(LeavesProtocol::onDisconnect);
    }

    private static void onDisconnect() {
        lavaRiptideEnable = true;
    }

    private static void lavaRiptide(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        lavaRiptideEnable = buf.readBoolean();
    }
}
