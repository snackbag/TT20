package net.snackbag.tt20.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
//? if >=26.1 {
/*import net.minecraft.server.dedicated.ServerWatchdog;
*///? } else {
import net.minecraft.server.dedicated.DedicatedServerWatchdog;
//? }
import net.snackbag.tt20.TT20;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=26.1 {
/*@Mixin(ServerWatchdog.class)
*///? } else {
@Mixin(DedicatedServerWatchdog.class)
//? }
public abstract class DedicatedServerWatchdogMixin {
    //? if >=26.1 {
    /*@ModifyExpressionValue(method = "run", at = @At(value = "FIELD", target = "Lnet/minecraft/server/dedicated/ServerWatchdog;maxTickTimeNanos:J", opcode = Opcodes.GETFIELD))
    *///? } else {
    @ModifyExpressionValue(method = "run", at = @At(value = "FIELD", target = "Lnet/minecraft/server/dedicated/DedicatedServerWatchdog;maxTickTime:J", opcode = Opcodes.GETFIELD))
    //? }
    private long cancelServerWatchdog(long original) {
        return TT20.config.serverWatchdog() ? original : Long.MAX_VALUE; // using Long.MAX_VALUE, because we can't cancel the if statement
    }
}
