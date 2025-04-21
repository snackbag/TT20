package net.snackbag.tt20.mixin.world;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ServerLevel;
import net.snackbag.tt20.Config;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @ModifyExpressionValue(
            method = "tickTime",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/WritableLevelData;getDayTime()J")
    )
    private long addMissingTicksToTime(long original) {
        if (!Config.ENABLED.get() || !Config.TIME_ACCELERATION.get()) return original;
        return original + TT20.TPS_CALCULATOR.applicableMissedTicks();
    }
}
