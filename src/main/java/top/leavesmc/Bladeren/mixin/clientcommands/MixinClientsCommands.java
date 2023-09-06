package top.leavesmc.Bladeren.mixin.clientcommands;

import net.earthcomputer.clientcommands.ClientCommands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.clientcommands.RandomManager;

@Dependencies(and = @Dependency(ModInfo.CLIENTCOMMANDS_MOD_ID))
@Mixin(ClientCommands.class)
public class MixinClientsCommands {
    @Inject(method = "onInitializeClient", at = @At("HEAD"), remap = false)
    private void onInit(CallbackInfo ci) {
        RandomManager.init();
    }
}
