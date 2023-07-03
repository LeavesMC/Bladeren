package top.leavesmc.Bladeren.compat.modmenu;

import top.leavesmc.Bladeren.ModInfo;

public class WrapperModMenuApiImpl extends ModMenuApiImpl {
    @Override
    public String getModIdCompat() {
        return ModInfo.MOD_ID;
    }
}
