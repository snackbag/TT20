package net.snackbag.tt20.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.dedicated.DedicatedServerWatchdog;
import net.snackbag.tt20.TT20;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DedicatedServerWatchdog.class)
public abstract class DedicatedServerWatchdogMixin {
    @ModifyExpressionValue(method = "run", at = @At(value = "FIELD", target = "Lnet/minecraft/server/dedicated/DedicatedServerWatchdog;maxTickTime:J", opcode = Opcodes.GETFIELD))
    private long cancelServerWatchdog(long original) {
        return TT20.config.serverWatchdog() ? original : Long.MAX_VALUE; // using Long.MAX_VALUE, because we can't cancel the if statement
    }
}
