package net.snackbag.tt20.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.dedicated.ServerWatchdog;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerWatchdog.class)
public class ServerWatchdogMixin {
    @ModifyExpressionValue(
            method = "run",
            //? if >=1.21.11 {
            /*at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;getNanos()J")
            *///?} else if >=1.20.3 {
            /*at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;getNanos()J")
            *///?} else {
            at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;getMillis()J")
            //?}
    )
    private long disableWatchdog(long original) {
        return TT20.config.serverWatchdog() ? original : 0;
    }
}