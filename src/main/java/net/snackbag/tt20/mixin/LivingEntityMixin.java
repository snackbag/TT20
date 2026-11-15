package net.snackbag.tt20.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow protected abstract void tickEffects();

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;tickEffects()V"))
    private void fixPotionDelayTick(CallbackInfo ci) {
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

    //? if >=26.1 {
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
        instance.invulnerableTime = (!TT20.config.enabled() || !TT20.config.iframeAcceleration()) ? value : TPSUtil.tt20(value, true);
    }
}
