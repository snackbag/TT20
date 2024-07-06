package net.snackbag.tt20.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.player.PlayerEntity;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    //? if <=1.20.6 {
    @ModifyReturnValue(method = "getMaxNetherPortalTime", at = @At("RETURN"))
    private int netherPortalTimeTT20(int original) {
        if (!TT20.config.enabled()) return original;
        if (original == 1) return original;

        return TPSUtil.tt20(original, false);
    }
    //?}

    @ModifyExpressionValue(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;sleepTimer:I", opcode = Opcodes.GETFIELD))
    private int tickTT20(int original) {
        if (!TT20.config.enabled()) return original;
        return original + TT20.TPS_CALCULATOR.applicableMissedTicks();
    }
}
