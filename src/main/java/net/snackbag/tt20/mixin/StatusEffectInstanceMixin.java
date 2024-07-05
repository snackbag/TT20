package net.snackbag.tt20.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.snackbag.tt20.TT20;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public class StatusEffectInstanceMixin {
    @Shadow private int duration;

    @Inject(method = "getDuration", at = @At(value = "HEAD"))
    private void durationTT20(CallbackInfoReturnable<Integer> cir) {
        if (!TT20.config.enabled()) return;
        duration = duration - TT20.TPS_CALCULATOR.applicableMissedTicks();
    }
}
