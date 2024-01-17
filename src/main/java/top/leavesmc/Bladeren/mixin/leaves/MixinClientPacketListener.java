package top.leavesmc.Bladeren.mixin.leaves;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.leavesmc.Bladeren.leaves.LeavesPayload;
import top.leavesmc.Bladeren.leaves.LeavesProtocol;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener extends ClientCommonPacketListenerImpl {

    protected MixinClientPacketListener(Minecraft minecraft, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraft, connection, commonListenerCookie);
    }

    @Inject(method = "handleLogin", at = @At("RETURN"))
    private void onGameJoined(ClientboundLoginPacket clientboundLoginPacket, CallbackInfo ci) {
        LeavesProtocol.gameJoined(minecraft.player);
    }

    @Inject(method = "handleUnknownCustomPayload", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V"), cancellable = true)
    private void onOnCustomPayload(CustomPacketPayload packetPayload, CallbackInfo ci) {
        if (packetPayload instanceof LeavesPayload payload) {
            LeavesProtocol.onPayload(payload);
            ci.cancel();
        }
    }
}
