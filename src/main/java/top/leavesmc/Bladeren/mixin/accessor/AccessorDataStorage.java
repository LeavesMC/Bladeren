package top.leavesmc.Bladeren.mixin.accessor;

import fi.dy.masa.minihud.util.DataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.leavesmc.Bladeren.ModInfo;

@Dependencies(and = @Dependency(ModInfo.MINIHUD_MOD_ID))
@Mixin(value = DataStorage.class, remap = false)
public interface AccessorDataStorage {

    @Accessor
    void setCarpetServer(boolean isCarpetServer);

    @Accessor
    void setServerTPSValid(boolean isServerTPSValid);

    @Accessor
    void setServerTPS(double tps);

    @Accessor
    void setServerMSPT(double mspt);
}
