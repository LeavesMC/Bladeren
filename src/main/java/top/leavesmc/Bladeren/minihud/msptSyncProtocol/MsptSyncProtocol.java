package top.leavesmc.Bladeren.minihud.msptSyncProtocol;

import fi.dy.masa.minihud.util.DataStorage;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.config.Configs;
import top.leavesmc.Bladeren.leaves.LeavesProtocol;
import top.leavesmc.Bladeren.mixin.accessor.AccessorDataStorage;

public class MsptSyncProtocol {

    private static final ResourceLocation MSPT_SYNC = ModInfo.id("mspt_sync");

    private static AccessorDataStorage dataStorage;

    public static void init() {
        LeavesProtocol.registerDataHandler(MSPT_SYNC, MsptSyncProtocol::updateMspt);
        LeavesProtocol.registerFeatureHandler("mspt_sync", MsptSyncProtocol::enableSync);
    }

    private static void enableSync(LocalPlayer player, CompoundTag tag) {
        if (tag.getString("Value").equals("true")) {
            dataStorage = (AccessorDataStorage) DataStorage.getInstance();
            CompoundTag data = new CompoundTag();
            data.putString("Value", Boolean.toString(Configs.msptSyncProtocol));
            LeavesProtocol.addFeatureBackData("mspt_sync", data);
        }
    }

    public static void modifyStatus() {
        CompoundTag data = new CompoundTag();
        data.putString("Value", Boolean.toString(Configs.msptSyncProtocol));
        LeavesProtocol.sendFeatureModify("mspt_sync", data);

        if (!Configs.msptSyncProtocol) {
            dataStorage.setServerTPSValid(false);
            dataStorage.setCarpetServer(false);
        } else {
            dataStorage = (AccessorDataStorage) DataStorage.getInstance();
        }
    }

    private static void updateMspt(LocalPlayer player, FriendlyByteBuf buf) {
        if (Configs.msptSyncProtocol) {
            dataStorage.setServerMSPT(buf.readDouble());
            dataStorage.setServerTPS(buf.readDouble());
            dataStorage.setServerTPSValid(true);
            dataStorage.setCarpetServer(true);
        }
    }
}
