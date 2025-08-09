package net.snackbag.tt20.mixin.world;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
//? if >=1.20.1 {
import net.minecraft.registry.Registries;
//?} else {
/*import net.minecraft.util.registry.Registry;
*///?}
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.chunk.WorldChunk$DirectBlockEntityTickInvoker")
public abstract class WorldChunkMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityTicker;tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/entity/BlockEntity;)V"))
    private <T extends BlockEntity> void onTick(BlockEntityTicker<T> instance, World world, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, Operation<Void> original) {
        original.call(instance, world, blockPos, blockState, blockEntity);
        if (!TT20.config.enabled()) return;
        if (!TT20.config.blockEntityAcceleration()) return;
        if (world.isClient()) return;
        //? if >=1.20.1 {
        if (!TT20.blockEntityMaskConfig.getMask().isOkay(Registries.BLOCK.getId(blockState.getBlock()))) return;
        //?} else {
        /*if (!TT20.blockEntityMaskConfig.getMask().isOkay(Registry.BLOCK.getId(blockState.getBlock()))) return;
        *///?}

        for (int i = 0; i < TT20.TPS_CALCULATOR.applicableMissedTicks(); i++) {
            original.call(instance, world, blockPos, blockState, blockEntity);
        }
    }
}
