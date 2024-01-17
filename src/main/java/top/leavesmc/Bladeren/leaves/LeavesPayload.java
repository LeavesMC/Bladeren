package top.leavesmc.Bladeren.leaves;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record LeavesPayload(FriendlyByteBuf byteBuf, ResourceLocation id) implements CustomPacketPayload {
    public LeavesPayload(ResourceLocation identifier, FriendlyByteBuf input) {
        this(new FriendlyByteBuf(input.readBytes(input.readableBytes())), identifier);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBytes(byteBuf);
    }
}
