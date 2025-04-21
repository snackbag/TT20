package net.snackbag.tt20.mixin.world;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.snackbag.tt20.Config;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.class)
public class BlockMixin {
    @ModifyReturnValue(method = "getDestroyProgress", at = @At("RETURN"))
    private float onDestroyProgress(float original, @Local Player player) {
        if (!Config.BLOCK_BREAKING_ACCELERATION.get() || !Config.ENABLED.get()) return original;
        if (player.level().isClientSide()) return original;
        return original * TPSCalculator.MAX_TPS / (float) TT20.TPS_CALCULATOR.getMostAccurateTPS();
    }
}
