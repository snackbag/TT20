package net.snackbag.tt20.mixin.world;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.AbstractBlock;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {
    @ModifyReturnValue(method = "calcBlockBreakingDelta", at = @At("RETURN"))
    private float onBlockBreakingCalc(float original) {
        if (!TT20.config.enabled()) return original;
        return original * 20 / (float) TT20.TPS_CALCULATOR.getMostAccurateTPS();
    }
}
