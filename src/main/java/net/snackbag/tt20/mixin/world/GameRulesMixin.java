package net.snackbag.tt20.mixin.world;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.GameRules;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRules.class)
public class GameRulesMixin {
    @ModifyReturnValue(method = "getInt", at = @At("RETURN"))
    private int randomTickSpeedAcceleration(int original, @Local(argsOnly = true) GameRules.Key<GameRules.IntRule> rule) {
        if (!TT20.config.enabled() || !TT20.config.randomTickSpeedAcceleration()) return original;
        if (!(rule == GameRules.RANDOM_TICK_SPEED)) return original;
        return (int) (original * TPSCalculator.MAX_TPS / (float) TT20.TPS_CALCULATOR.getMostAccurateTPS());
    }
}
