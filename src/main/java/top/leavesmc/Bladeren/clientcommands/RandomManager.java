package top.leavesmc.Bladeren.clientcommands;

import com.mojang.brigadier.CommandDispatcher;
import lombok.Setter;
import net.earthcomputer.clientcommands.command.FishCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;
import top.leavesmc.Bladeren.config.Configs;
import top.leavesmc.Bladeren.leaves.LeavesProtocol;

public class RandomManager {

    @Setter
    private static CommandDispatcher<FabricClientCommandSource> savedDispatcher;
    @Setter
    private static CommandBuildContext savedRegistryAccess;

    private static final String LOOT_WORLD_RANDOM = "loot_world_random";
    private static final String VANILLA_RANDOM = "use_vanilla_random";

    public static void init() {
        LeavesProtocol.registerHelloEndTask(RandomManager::onHelloEnd);
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
        return isVanillaRandom() && LeavesProtocol.isFeatureEnable(LOOT_WORLD_RANDOM);
    }
}
