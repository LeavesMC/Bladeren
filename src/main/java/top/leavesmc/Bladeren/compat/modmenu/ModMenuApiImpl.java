package top.leavesmc.Bladeren.compat.modmenu;

import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.gui.GuiConfigs;
import top.hendrixshen.magiclib.compat.modmenu.ModMenuCompatApi;

public class ModMenuApiImpl implements ModMenuCompatApi {
    @Override
    public ConfigScreenFactoryCompat<?> getConfigScreenFactoryCompat() {
        return (screen) -> {
            GuiConfigs gui = GuiConfigs.getInstance();
            gui.setParent(screen);
            return gui;
        };
    }

    @Override
    public String getModIdCompat() {
        return ModInfo.CURRENT_MOD_ID;
    }
}
