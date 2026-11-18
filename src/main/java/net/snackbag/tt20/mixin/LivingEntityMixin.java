package net.snackbag.tt20.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow protected abstract void tickEffects();

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;tickEffects()V"))
    private void tt20$effect(CallbackInfo ci) {
        if (!TT20.config.enabled() || !TT20.config.potionEffectAcceleration()) return;
        //? if >=1.20.1 {
        if (((Entity)(Object)this).level().isClientSide()) return;
        //?} else {
        /*if (((Entity)(Object)this).level.isClientSide()) return;
        *///?}
        for (int i = 0; i < TT20.TPS_CALCULATOR.applicableMissedTicks(); i++) {
            tickEffects();
        }
    }

    //? if >=1.21.2 {
    @ModifyExpressionValue(
            method = "hurtServer",
            at = @At(value = "CONSTANT", args = "floatValue=10.0")
    )
    //?} else {
    /*@ModifyExpressionValue(
            method = "hurt",
            at = @At(value = "CONSTANT", args = "floatValue=10.0")
    )
    *///?}
    private float tt20$iframeThreshold(float original) {
        if (!TT20.config.enabled() || !TT20.config.iframeAcceleration()) return original;
        return TPSUtil.tt20(original, true);
    }

    //? if >=26.3 {
    /*@Redirect(
            method = "hurtServer",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/world/entity/LivingEntity;damageCooldownTime:I",
                    opcode = Opcodes.PUTFIELD))
    *///?} elif >=1.21.2 {
    @Redirect(
            method = "hurtServer",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/world/entity/LivingEntity;invulnerableTime:I",
                    opcode = Opcodes.PUTFIELD))
    //?} else {
    /*@Redirect(
            method = "hurt",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/world/entity/LivingEntity;invulnerableTime:I",
                    opcode = Opcodes.PUTFIELD))
    *///?}
    private void tt20$iframe(LivingEntity instance, int value) {
        //? if >=26.3 {
        /*instance.damageCooldownTime = (!TT20.config.enabled() || !TT20.config.iframeAcceleration()) ? value : TPSUtil.tt20(value, true);
        *///?} else {
        instance.invulnerableTime = (!TT20.config.enabled() || !TT20.config.iframeAcceleration()) ? value : TPSUtil.tt20(value, true);
        //?}
    }
}
