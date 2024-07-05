package net.snackbag.tt20.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.ItemEntity;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Shadow private int pickupDelay;

    @Inject(method = "tick", at = @At("HEAD"))
    private void pickupDelayTT20(CallbackInfo ci) {
        if (!TT20.config.enabled()) return;

        if (pickupDelay == 0) return;

        if (pickupDelay - TT20.TPS_CALCULATOR.applicableMissedTicks() <= 0) {
            pickupDelay = 0;
            return;
        }

        pickupDelay = pickupDelay - TT20.TPS_CALCULATOR.applicableMissedTicks();
    }
}