package net.snackbag.tt20.mixin;

import net.minecraft.server.MinecraftServer;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "tickServer", at = @At("TAIL"))
    private void tt20$serverTick(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        TT20.TPS_CALCULATOR.onTick();
    }
}
