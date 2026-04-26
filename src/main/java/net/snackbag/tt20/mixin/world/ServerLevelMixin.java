package net.snackbag.tt20.mixin.world;

import net.minecraft.server.level.ServerLevel;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.mixin.accessor.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    //? if >=26.1 {
    /*@Inject(method = "tickTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;setGameTime(J)V"))
    *///?} else {
    @Inject(method = "tickTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V"))
    //?}
    private void applyMissedTicks(CallbackInfo ci) {
        if (!TT20.config.enabled() || !TT20.config.timeAcceleration()) return;

        ServerLevel self = (ServerLevel) (Object) this;

        //? if >=26.1 {
        /*((ServerLevelAccessor) self).getServerLevelData().setGameTime(self.getLevelData().getGameTime() + TT20.TPS_CALCULATOR.applicableMissedTicks());
        *///?} else {
        self.setDayTime(self.getLevelData().getDayTime() + TT20.TPS_CALCULATOR.applicableMissedTicks());
        //?}
    }
}
