package top.leavesmc.Bladeren;

import net.fabricmc.api.ClientModInitializer;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.hendrixshen.magiclib.malilib.impl.ConfigHandler;
import top.hendrixshen.magiclib.malilib.impl.ConfigManager;
import top.leavesmc.Bladeren.config.Configs;
import top.leavesmc.Bladeren.leaves.LeavesProtocol;

public class BladerenMod implements ClientModInitializer {
    private static final int CONFIG_VERSION = 1;

    @Dependencies(and = {
            @Dependency(value = ModInfo.MINIHUD_MOD_ID, versionPredicate = ">=0.22.0", optional = true),
            @Dependency(value = ModInfo.CLIENTCOMMANDS_MOD_ID, versionPredicate = ">=2.8.2", optional = true),
    })
    @Override
    public void onInitializeClient() {
        ConfigManager cm = ConfigManager.get(ModInfo.MOD_ID);
        cm.parseConfigClass(Configs.class);
        ModInfo.configHandler = new ConfigHandler(ModInfo.MOD_ID, cm, CONFIG_VERSION);
        ConfigHandler.register(ModInfo.configHandler);
        Configs.init(cm);

        LeavesProtocol.init();
    }
}
