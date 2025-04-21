package net.snackbag.tt20.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @ModifyReturnValue(method = "getPortalWaitTime", at = @At("RETURN"))
    private int netherPortalTimeTT20(int original) {
        if (!TT20.config.enabled() || !TT20.config.portalAcceleration()) return original;
        if (((Entity)(Object)this).level().isClientSide()) return original;
        if (original == 1) return original;

        return TPSUtil.tt20(original, false);
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;sleepCounter:I", opcode = Opcodes.GETFIELD))
    private int tickTT20(int original) {
        if (!TT20.config.enabled() || !TT20.config.sleepingAcceleration()) return original;
        if (((Entity)(Object)this).level().isClientSide()) return original;
        return original + TT20.TPS_CALCULATOR.applicableMissedTicks();
    }
}