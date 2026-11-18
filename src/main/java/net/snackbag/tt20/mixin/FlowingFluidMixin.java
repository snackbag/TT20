package net.snackbag.tt20.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.level.material.WaterFluid;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({WaterFluid.class, LavaFluid.class})
public abstract class FlowingFluidMixin {
    @ModifyReturnValue(method = "getTickDelay", at = @At("RETURN"))
    private int tt20$fluid(int original) {
        if (!TT20.config.enabled() || !TT20.config.fluidAcceleration()) return original;
        return TPSUtil.tt20(original, true);
    }
}
