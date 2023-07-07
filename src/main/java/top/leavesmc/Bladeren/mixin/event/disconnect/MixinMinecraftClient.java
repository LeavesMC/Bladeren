package top.leavesmc.Bladeren.mixin.event.disconnect;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.leavesmc.Bladeren.event.DisconnectEvent;

@Mixin(Minecraft.class)
public abstract class MixinMinecraftClient {

    @Inject(method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At(value = "HEAD"))
    private void onDisconnect(CallbackInfo ci) {
        if (!Minecraft.getInstance().hasSingleplayerServer()) {
            DisconnectEvent.onDisconnect();
        }
    }
}
