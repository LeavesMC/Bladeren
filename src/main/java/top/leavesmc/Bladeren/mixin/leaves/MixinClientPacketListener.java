package top.leavesmc.Bladeren.mixin.leaves;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.leavesmc.Bladeren.leaves.LeavesProtocol;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "handleLogin", at = @At("RETURN"))
    private void onGameJoined(ClientboundLoginPacket packet, CallbackInfo info) {
        LeavesProtocol.gameJoined(minecraft.player);
    }

    @Inject(method = "handleCustomPayload", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V"), cancellable = true)
    private void onOnCustomPayload(ClientboundCustomPayloadPacket packet, CallbackInfo ci) {
        LeavesProtocol.onPayload(packet.getIdentifier(), packet.getData());
        ci.cancel();
    }
}
