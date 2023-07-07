package top.leavesmc.Bladeren.mixin.minecraft.lavaRiptide;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.leavesmc.Bladeren.config.Configs;
import top.leavesmc.Bladeren.leaves.LeavesProtocol;

@Mixin(TridentItem.class)
public class MixinTridentItem {

    @Redirect(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    private boolean isInWaterOrLave(Player player) {
        return player.isInWaterOrRain() || (Configs.lavaRiptide && LeavesProtocol.lavaRiptideEnable && player.isInLava());
    }

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    private boolean checkInWaterOrLave(Player player) {
        return player.isInWaterOrRain() || (Configs.lavaRiptide && LeavesProtocol.lavaRiptideEnable && player.isInLava());
    }
}
