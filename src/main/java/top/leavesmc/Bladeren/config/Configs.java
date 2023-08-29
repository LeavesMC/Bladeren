package top.leavesmc.Bladeren.config;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.hendrixshen.magiclib.malilib.api.annotation.Config;
import top.hendrixshen.magiclib.malilib.api.annotation.Hotkey;
import top.hendrixshen.magiclib.malilib.impl.ConfigManager;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.gui.GuiConfigs;
import top.leavesmc.Bladeren.minihud.msptSyncProtocol.MsptSyncProtocol;

public class Configs {
    // GENERIC
    @Config(category = ConfigCategory.GENERIC)
    public static boolean debug = false;

    @Hotkey(hotkey = "N,C")
    @Config(category = ConfigCategory.GENERIC)
    public static ConfigHotkey openConfigGui;

    // MINECRAFT
    @Hotkey
    @Config(category = ConfigCategory.MINECRAFT)
    public static boolean lavaRiptide = false;

    // MINIHUD
    @Hotkey
    @Config(category = ConfigCategory.MINIHUD, dependencies = @Dependencies(and = @Dependency(ModInfo.MINIHUD_MOD_ID)))
    public static boolean msptSyncProtocol = true;

    public static void init(ConfigManager cm) {
        // GENERIC
        cm.setValueChangeCallback("debug", option -> {
            if (debug) {
                Configurator.setLevel(ModInfo.MOD_ID, Level.toLevel("DEBUG"));
            } else {
                Configurator.setLevel(ModInfo.MOD_ID, Level.toLevel("INFO"));
            }
            GuiConfigs.getInstance().reDraw();
        });

        if (debug) {
            Configurator.setLevel(ModInfo.MOD_ID, Level.toLevel("DEBUG"));
        }

        openConfigGui.getKeybind().setCallback((keyAction, iKeybind) -> {
            GuiConfigs screen = GuiConfigs.getInstance();
            screen.setParent(Minecraft.getInstance().screen);
            Minecraft.getInstance().setScreen(screen);
            return true;
        });

        // MINIHUD
        cm.setValueChangeCallback("msptSyncProtocol", option -> {
            MsptSyncProtocol.modifyStatus();
        });
    }

    public static class ConfigCategory {
        public static final String GENERIC = "generic";
        public static final String MINECRAFT = "minecraft";
        public static final String MINIHUD = "minihud";
    }
}