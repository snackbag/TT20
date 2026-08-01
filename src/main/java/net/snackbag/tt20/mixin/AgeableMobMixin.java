package net.snackbag.tt20.mixin;

import net.minecraft.world.entity.AgeableMob;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AgeableMob.class)
public abstract class AgeableMobMixin {
    @Inject(method = "aiStep", at = @At("TAIL"))
    private void accelerateAge(CallbackInfo ci) {
        AgeableMob mob = (AgeableMob) (Object) this;

        if (!TT20.config.enabled() || !TT20.config.mobTimerAcceleration()) return;
        //? if >=1.20.1 {
        if (mob.level().isClientSide()) return;
        //?} else {
        /*if (mob.level.isClientSide()) return;
        *///?}

        int missedTicks = TT20.TPS_CALCULATOR.applicableMissedTicks();
        int age = mob.getAge();
        if (missedTicks == 0 || age == 0) return;

        if (age < 0) {
            mob.setAge(Math.min(0, age + missedTicks));
        } else {
            mob.setAge(Math.max(0, age - missedTicks));
        }
    }
}
