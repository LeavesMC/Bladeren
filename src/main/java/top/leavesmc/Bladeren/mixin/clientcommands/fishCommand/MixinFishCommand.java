package top.leavesmc.Bladeren.mixin.clientcommands.fishCommand;

import com.mojang.brigadier.CommandDispatcher;
import net.earthcomputer.clientcommands.MultiVersionCompat;
import net.earthcomputer.clientcommands.command.FishCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.clientcommands.RandomManager;
import top.leavesmc.Bladeren.config.Configs;

@Dependencies(and = @Dependency(ModInfo.CLIENTCOMMANDS_MOD_ID))
@Mixin(FishCommand.class)
public class MixinFishCommand {

    @Inject(method = "register", at = @At("HEAD"), remap = false)
    private static void onRegister(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext registryAccess, CallbackInfo ci) {
        RandomManager.setSavedDispatcher(dispatcher);
        RandomManager.setSavedRegistryAccess(registryAccess);
    }

    @Redirect(
            method = "register",
            at = @At(value = "INVOKE", target = "Lnet/earthcomputer/clientcommands/MultiVersionCompat;getProtocolVersion()I"),
            remap = false
    )
    private static int getProtocolVersion(MultiVersionCompat instance) {
        if (Configs.fishCommand && RandomManager.isFishCommand()) {
            return 762;
        } else {
            return instance.getProtocolVersion();
        }
    }
}
