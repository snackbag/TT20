package net.snackbag.tt20.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
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
    @ModifyConstant(method = "hurtServer", constant = @Constant(intValue = 20, ordinal = 0))
    //?} else {
    /*@ModifyConstant(method = "hurt", constant = @Constant(intValue = 20, ordinal = 0))
    *///?}
    private int tt20$IframeFix(int constant) {
        if (!TT20.config.enabled() || !TT20.config.iframeAcceleration()) return constant;
        return TPSUtil.tt20(constant, true);
    }
}
