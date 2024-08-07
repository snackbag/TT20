package net.snackbag.tt20.mixin.world;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {
    @ModifyReturnValue(method = "calcBlockBreakingDelta", at = @At("RETURN"))
    private float onBlockBreakingCalc(float original, @Local PlayerEntity player) {
        if (!TT20.config.enabled() || !TT20.config.blockBreakingAcceleration()) return original;
        if (player.getWorld().isClient()) return original;
        return original * TPSCalculator.MAX_TPS / (float) TT20.TPS_CALCULATOR.getMostAccurateTPS();
    }
}
