package top.leavesmc.Bladeren.clientcommands;

import com.mojang.brigadier.CommandDispatcher;
import lombok.Setter;
import net.earthcomputer.clientcommands.command.FishCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.config.Configs;
import top.leavesmc.Bladeren.leaves.LeavesProtocol;

public class RandomManager {

    @Setter
    private static CommandDispatcher<FabricClientCommandSource> savedDispatcher;
    @Setter
    private static CommandBuildContext savedRegistryAccess;

    private static final String RNG_FISHING = "rng_fishing";
    private static final String VANILLA_RANDOM = "use_vanilla_random";

    public static final ResourceLocation RNG_FISHING_ID = ModInfo.id("fishing_loot_seed");

    public static long fishingLootSeed = 0L;

    public static void init() {
        LeavesProtocol.registerHelloEndTask(RandomManager::onHelloEnd);
        LeavesProtocol.registerFeatureHandler(RNG_FISHING, RandomManager::enableRNGFishing);
    }

    private static void enableRNGFishing(LocalPlayer player, CompoundTag tag) {
        if (tag.getString("Value").equals("true")) {
            CompoundTag data = new CompoundTag();
            data.putString("Value", Boolean.toString(Configs.fishCommand));
            LeavesProtocol.addFeatureBackData(RNG_FISHING, data);
        }
    }

    private static void onHelloEnd() {
        if (Configs.fishCommand) {
            FishCommand.register(savedDispatcher, savedRegistryAccess);
        }
    }

    public static boolean isVanillaRandom() {
        return LeavesProtocol.isFeatureEnable(VANILLA_RANDOM);
    }

    public static boolean isFishCommand() {
        return isVanillaRandom() && LeavesProtocol.isFeatureEnable(RNG_FISHING);
    }
}
