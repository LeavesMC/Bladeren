package top.leavesmc.Bladeren.mixin.clientcommands.fishCommand;

import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcfeature.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.clientcommands.RandomManager;

@Dependencies(and = @Dependency(ModInfo.CLIENTCOMMANDS_MOD_ID))
@Mixin(LootContext.class)
public class MixinLootContext {
    @Inject(method = "<init>(JLcom/seedfinding/mccore/version/MCVersion;)V", at = @At("RETURN"), remap = false)
    private void onInit(long lootTableSeed, MCVersion version, CallbackInfo ci) {
        RandomManager.fishingLootSeed = lootTableSeed;
    }
}
