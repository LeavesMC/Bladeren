package top.leavesmc.Bladeren.mixin.clientcommands.fishCommand;

import io.netty.buffer.Unpooled;
import net.earthcomputer.clientcommands.Configs;
import net.earthcomputer.clientcommands.features.FishingCracker;
import net.earthcomputer.clientcommands.mixin.CheckedRandomAccessor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.clientcommands.RandomManager;
import top.leavesmc.Bladeren.leaves.LeavesProtocol;

import java.util.OptionalLong;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Dependencies(and = @Dependency(ModInfo.CLIENTCOMMANDS_MOD_ID))
@Mixin(FishingCracker.class)
public abstract class MixinFishingCracker {

    @Shadow(remap = false)
    @Final
    private static ScheduledExecutorService DELAY_EXECUTOR;

    @Unique
    private static long realSeed = 0L;

    @Inject(method = "getSeed", at = @At("RETURN"), remap = false)
    private static void onGetSeed(UUID uuid, CallbackInfoReturnable<OptionalLong> cir) {
        if (cir.getReturnValue().isPresent() && RandomManager.isFishCommand()) {
            RandomSource random = RandomSource.create(cir.getReturnValue().getAsLong() ^ 25214903917L);
            Mth.createInsecureUUID(random);
            realSeed = ((CheckedRandomAccessor) random).getSeed().get();
        }
    }

    @Inject(method = "processBobberSpawn", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 2), remap = false)
    private static void onGetGoodFishing(UUID fishingBobberUUID, Vec3 pos, Vec3 velocity, CallbackInfo ci) {
        long lootSeed = RandomManager.fishingLootSeed;
        DELAY_EXECUTOR.schedule(() -> {
            if (Configs.getFishingManipulation().isEnabled()) {
                if (realSeed != 0L && RandomManager.fishingLootSeed != 0L) {
                    FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                    buf.writeLong(realSeed);
                    buf.writeLong(lootSeed);
                    buf.writeInt(0);
                    LeavesProtocol.sendPacket(RandomManager.RNG_FISHING_ID, buf);
                }
            }
        }, 200, TimeUnit.MILLISECONDS);
    }
}
