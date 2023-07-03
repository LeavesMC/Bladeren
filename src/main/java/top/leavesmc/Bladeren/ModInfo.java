package top.leavesmc.Bladeren;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.compat.minecraft.api.network.chat.ComponentCompatApi;
import top.hendrixshen.magiclib.malilib.impl.ConfigHandler;
import top.hendrixshen.magiclib.language.api.I18n;

import net.minecraft.network.chat.MutableComponent;

public class ModInfo {
    public static final String MINIHUD_MOD_ID = "minihud";
    public static String MOD_ID = "@MOD_IDENTIFIER@";
    public static final String CURRENT_MOD_ID = "@MOD_IDENTIFIER@-@MINECRAFT_VERSION_IDENTIFY@";
    public static final String MOD_NAME = FabricLoader.getInstance().getModContainer(CURRENT_MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getName();
    public static final String MOD_VERSION = FabricLoader.getInstance().getModContainer(CURRENT_MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static ConfigHandler configHandler;

    public static String translate(String key, Object... objects) {
        return I18n.get(ModInfo.MOD_ID + "." + key, objects);
    }

    public static @NotNull MutableComponent translatable(String key, Object... objects) {
        return ComponentCompatApi.translatable(ModInfo.MOD_ID + "." + key, objects);
    }

    @Contract("_ -> new")
    public static @NotNull ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}

