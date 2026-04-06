package net.snackbag.tt20.mixin.world;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;

//? if >=1.20.1
import net.minecraft.core.registries.Registries;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.level.chunk.LevelChunk$BoundTickingBlockEntity")
public abstract class WorldChunkMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;tick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BlockEntity;)V"))
    private <T extends BlockEntity> void onTick(BlockEntityTicker<T> instance, Level world, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, Operation<Void> original) {
        original.call(instance, world, blockPos, blockState, blockEntity);
        if (!TT20.config.enabled()) return;
        if (!TT20.config.blockEntityAcceleration()) return;
        if (world.isClientSide()) return;
        //? if >=1.21.2 {
        /*Registry<Block> registry = world.registryAccess().lookupOrThrow(Registries.BLOCK);
        if (!TT20.blockEntityMaskConfig.getMask().isOkay(registry.getKey(blockState.getBlock()))) return;
        *///?} else if >=1.20.1 {
        Registry<Block> registry = world.registryAccess().registryOrThrow(Registries.BLOCK);
        if (!TT20.blockEntityMaskConfig.getMask().isOkay(registry.getKey(blockState.getBlock()))) return;
        //?} else {
        /*if (!TT20.blockEntityMaskConfig.getMask().isOkay(Registry.BLOCK.getKey(blockState.getBlock()))) return;
        *///?}

        for (int i = 0; i < TT20.TPS_CALCULATOR.applicableMissedTicks(); i++) {
            original.call(instance, world, blockPos, blockState, blockEntity);
        }
    }
}
