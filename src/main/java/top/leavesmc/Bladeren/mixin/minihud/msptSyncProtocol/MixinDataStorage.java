package top.leavesmc.Bladeren.mixin.minihud.msptSyncProtocol;

import fi.dy.masa.minihud.util.DataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.minihud.msptSyncProtocol.MsptSyncProtocol;

@Dependencies(and = @Dependency(ModInfo.MINIHUD_MOD_ID))
@Mixin(value = DataStorage.class, remap = false)
public class MixinDataStorage {
    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void onInit(CallbackInfo ci) {
        MsptSyncProtocol.init();
    }
}
