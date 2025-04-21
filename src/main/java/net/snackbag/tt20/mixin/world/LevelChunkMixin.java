package net.snackbag.tt20.mixin.world;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.registries.ForgeRegistries;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelChunk.BoundTickingBlockEntity.class)
public abstract class LevelChunkMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;tick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BlockEntity;)V"))
    private <T extends BlockEntity> void onTick(BlockEntityTicker<T> instance, Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, Operation<Void> original) {
        original.call(instance, level, blockPos, blockState, blockEntity);
        if (!TT20.config.enabled()) return;
        if (!TT20.config.blockEntityAcceleration()) return;
        if (level.isClientSide()) return;
        if (!TT20.blockEntityMaskConfig.getMask().isOkay(ForgeRegistries.BLOCKS.getKey(blockState.getBlock()))) return;

        for (int i = 0; i < TT20.TPS_CALCULATOR.applicableMissedTicks(); i++) {
            original.call(instance, level, blockPos, blockState, blockEntity);
        }
    }


}