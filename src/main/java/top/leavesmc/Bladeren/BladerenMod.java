package top.leavesmc.Bladeren;

import top.leavesmc.Bladeren.config.Configs;
import net.fabricmc.api.ClientModInitializer;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.hendrixshen.magiclib.malilib.impl.ConfigHandler;
import top.hendrixshen.magiclib.malilib.impl.ConfigManager;

public class BladerenMod implements ClientModInitializer {
    private static final int CONFIG_VERSION = 1;

    @Dependencies(and = {
            @Dependency(value = "itemscoller", versionPredicate = ">=0.16.0", optional = true),
            @Dependency(value = "litematica", versionPredicate = ">=0.11.0", optional = true),
            @Dependency(value = "minihud", versionPredicate = ">=0.22.0", optional = true),
            @Dependency(value = "tweakeroo", versionPredicate = ">=0.13.1", optional = true)
    })
    @Override
    public void onInitializeClient() {
        ConfigManager cm = ConfigManager.get(ModInfo.MOD_ID);
        cm.parseConfigClass(Configs.class);
        ModInfo.configHandler = new ConfigHandler(ModInfo.MOD_ID, cm, CONFIG_VERSION);
        ConfigHandler.register(ModInfo.configHandler);
        Configs.init(cm);
    }
}
