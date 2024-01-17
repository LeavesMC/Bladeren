package top.leavesmc.Bladeren.mixin.leaves;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.leaves.LeavesPayload;

@Mixin(ClientboundCustomPayloadPacket.class)
public class MixinClientboundCustomPayloadPacket {
    @Inject(method = "readPayload", at = @At(value = "HEAD"), cancellable = true)
    private static void readPayload(ResourceLocation id, FriendlyByteBuf buf, CallbackInfoReturnable<CustomPacketPayload> cir) {
        if (id.getNamespace().equals(ModInfo.MOD_ID)) {
            cir.setReturnValue(new LeavesPayload(id, buf));
        }
    }
}
