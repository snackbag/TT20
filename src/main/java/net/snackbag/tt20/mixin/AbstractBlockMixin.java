package net.snackbag.tt20.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Inject(method = "calcBlockBreakingDelta", at = @At("RETURN"), cancellable = true)
    private void onBlockBreakingCalc(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (!TT20.config.enabled()) return;
        TT20.LOGGER.info(String.valueOf(cir.getReturnValue() * 20 / TT20.TPS_CALCULATOR.getMostAccurateTPS()));
        cir.setReturnValue((float) (cir.getReturnValue() * 20 / TT20.TPS_CALCULATOR.getMostAccurateTPS()));
    }
}
