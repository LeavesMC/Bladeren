package top.leavesmc.Bladeren.mixin.minihud.msptSyncProtocol;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.config.Configs;
import top.leavesmc.Bladeren.minihud.msptSyncProtocol.MsptSyncProtocol;

@Dependencies(and = @Dependency(ModInfo.MINIHUD_MOD_ID))
@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {
    @Inject(method = "handleLogin", at = @At("RETURN"))
    private void onGameJoined(ClientboundLoginPacket packet, CallbackInfo info) {
        if (Configs.msptSyncProtocol) {
            MsptSyncProtocol.onPlayerJoin();
        }
    }
}
