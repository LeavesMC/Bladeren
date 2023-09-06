package top.leavesmc.Bladeren.mixin.clientcommands.disableNotVanillaWarn;

import net.earthcomputer.clientcommands.ServerBrandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.clientcommands.RandomManager;
import top.leavesmc.Bladeren.config.Configs;

@Dependencies(and = @Dependency(ModInfo.CLIENTCOMMANDS_MOD_ID))
@Mixin(ServerBrandManager.class)
public abstract class MixinServerBrandManager {

    @Shadow(remap = false)
    public static boolean isVanilla() {
        return false;
    }

    @Redirect(
            method = "rngWarning",
            at = @At(value = "INVOKE", target = "Lnet/earthcomputer/clientcommands/ServerBrandManager;isVanilla()Z"),
            remap = false
    )
    private static boolean isVanillaOrLeaves() {
        return isVanilla() || (Configs.disableNotVanillaWarn && RandomManager.isVanillaRandom());
    }
}
