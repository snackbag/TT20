package net.snackbag.tt20.mixin.world;

import net.minecraft.world.level.GameRules;
import net.snackbag.tt20.Config;
import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSCalculator;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRules.class)
public class GameRulesMixin {
    @ModifyReturnValue(method = "getInt", at = @At("RETURN"))
    private int randomTickSpeedAcceleration(int original, @Local(argsOnly = true) GameRules.Key<GameRules.IntegerValue> rule) {
        if (!Config.ENABLED.get() || !Config.RANDOM_TICKSPEED_ACCELERATION.get()) return original;
        if (!(rule == GameRules.RULE_RANDOMTICKING)) return original;
        return (int) (original * TPSCalculator.MAX_TPS / (float) TT20.TPS_CALCULATOR.getMostAccurateTPS());
    }
}
