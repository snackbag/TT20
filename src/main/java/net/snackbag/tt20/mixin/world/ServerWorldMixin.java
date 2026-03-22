package net.snackbag.tt20.mixin.world;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
//? if >=26.1 {
/*import net.minecraft.server.level.ServerLevel;
*///? } else {
import net.minecraft.server.world.ServerWorld;
//? }
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=26.1 {
/*@Mixin(ServerLevel.class)
*///? } else {
@Mixin(ServerWorld.class)
 //? }
public abstract class ServerWorldMixin {
    @ModifyExpressionValue(
            //? if >=26.1 {
            /*method = "tickTime",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/WritableLevelData;getGameTime()J")
            *///? } else {
            method = "tickTime",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/MutableWorldProperties;getTimeOfDay()J")
            //? }
    )
    private long addMissingTicksToTime(long original) {
        if (!TT20.config.enabled() || !TT20.config.timeAcceleration()) return original;
        return original + TT20.TPS_CALCULATOR.applicableMissedTicks();
    }
}
