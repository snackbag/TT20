package net.snackbag.tt20.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.BowItem;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BowItem.class)
public class BowItemMixin {
    @ModifyReturnValue(method = "getPullProgress", at = @At("RETURN"))
    private static float bowProgress(float original) {
        if (!TT20.config.enabled() || !TT20.config.bowAcceleration()) return original;

        return original * TPSCalculator.MAX_TPS / (float) TT20.TPS_CALCULATOR.getMostAccurateTPS();
    }
}
