package net.snackbag.tt20.mixin.world;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
//? if (>=1.21.11) {
/*import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
*///?} else {
import net.minecraft.world.level.GameRules;
//?}
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRules.class)
public class GameRulesMixin {
    //? if (>=1.21.11) {
    /*@ModifyReturnValue(method = "get", at = @At("RETURN"))
    private Object randomTickSpeedAcceleration(Object original, @Local(argsOnly = true) GameRule<?> gameRule) {
    *///?} else {
    @ModifyReturnValue(method = "getInt", at = @At("RETURN"))
    private int randomTickSpeedAcceleration(int original, @Local(argsOnly = true) GameRules.Key<GameRules.IntegerValue> key) {
    //?}
        if (!TT20.config.enabled() || !TT20.config.randomTickSpeedAcceleration()) return original;

        //? if (>=1.21.11) {
        /*if (gameRule != GameRules.RANDOM_TICK_SPEED) return original;

        if (!(original instanceof Integer value)) return original;
        return (int) (value * TPSCalculator.MAX_TPS / (float) TT20.TPS_CALCULATOR.getMostAccurateTPS());
        *///?} else {
        if (key != GameRules.RULE_RANDOMTICKING) return original;

        return (int) (original * TPSCalculator.MAX_TPS / (float) TT20.TPS_CALCULATOR.getMostAccurateTPS());
        //?}
    }
}
