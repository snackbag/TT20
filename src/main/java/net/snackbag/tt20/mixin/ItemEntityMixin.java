package net.snackbag.tt20.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.ItemEntity;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @ModifyExpressionValue(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/ItemEntity;pickupDelay:I", opcode = Opcodes.GETFIELD))
    private int pickupDelayTT20(int original) {
        if (!TT20.config.enabled()) return original;
        TT20.LOGGER.info(original + " " + TPSUtil.tt20(original, true));
        return Math.min(TPSUtil.tt20(original, true), 3);
    }
}
