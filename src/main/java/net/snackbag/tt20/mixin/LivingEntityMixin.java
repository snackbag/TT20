package net.snackbag.tt20.mixin;

import net.minecraft.entity.LivingEntity;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow protected abstract void tickStatusEffects();

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;tickStatusEffects()V"))
    private void fixPotionDelayTick(CallbackInfo ci) {
        if (!TT20.config.enabled() || !TT20.config.potionEffectAcceleration()) return;

        for (int i = 0; i < TT20.TPS_CALCULATOR.applicableMissedTicks(); i++) {
            tickStatusEffects();
        }
    }
}
