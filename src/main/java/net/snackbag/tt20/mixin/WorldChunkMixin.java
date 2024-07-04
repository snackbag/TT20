package net.snackbag.tt20.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldChunk.DirectBlockEntityTickInvoker.class)
public class WorldChunkMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityTicker;tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/entity/BlockEntity;)V"))
    private <T extends BlockEntity> void onTick(BlockEntityTicker<T> instance, World world, BlockPos blockPos, BlockState blockState, T t) {
        instance.tick(world, blockPos, blockState, t);
        if (!TT20.config.enabled()) return;

        for (int i = 0; i < TT20.TPS_CALCULATOR.applicableMissedTicks(); i++) {
            instance.tick(world, blockPos, blockState, t);
        }
    }
}
