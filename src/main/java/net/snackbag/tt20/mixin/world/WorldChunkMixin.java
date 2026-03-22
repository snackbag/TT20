package net.snackbag.tt20.mixin.world;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if >=26.1 {
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
*///?} else {
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
//?}

//? if >=1.20.1 && <26.1 {
import net.minecraft.registry.Registries;
//?} else if <26.1 {
/*import net.minecraft.util.registry.Registry;
*///?}

import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=26.1 {
/*@Mixin(LevelChunk.BoundTickingBlockEntity.class)
*///?} else {
@Mixin(WorldChunk.DirectBlockEntityTickInvoker.class)
//?}
public abstract class WorldChunkMixin {
    //? if >=26.1 {
    /*@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;tick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BlockEntity;)V"))
    private <T extends BlockEntity> void onTick(BlockEntityTicker<T> instance, Level world, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, Operation<Void> original) {
    *///?} else {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityTicker;tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/entity/BlockEntity;)V"))
    private <T extends BlockEntity> void onTick(BlockEntityTicker<T> instance, World world, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, Operation<Void> original) {
    //?}
        original.call(instance, world, blockPos, blockState, blockEntity);
        if (!TT20.config.enabled()) return;
        if (!TT20.config.blockEntityAcceleration()) return;
        //? if >=26.1 {
        /*if (world.isClientSide()) return;
        *///?} else {
        if (world.isClient()) return;
        //?}

        //? if >=26.1 {
        /*if (!TT20.blockEntityMaskConfig.getMask().isOkay(BuiltInRegistries.BLOCK.getKey(blockState.getBlock()))) return;
        *///?} else if >=1.20.1 {
        if (!TT20.blockEntityMaskConfig.getMask().isOkay(Registries.BLOCK.getId(blockState.getBlock()))) return;
        //?} else {
        /*if (!TT20.blockEntityMaskConfig.getMask().isOkay(Registry.BLOCK.getId(blockState.getBlock()))) return;
        *///?}

        for (int i = 0; i < TT20.TPS_CALCULATOR.applicableMissedTicks(); i++) {
            original.call(instance, world, blockPos, blockState, blockEntity);
        }
    }
}
