package net.snackbag.tt20.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
//? if >=26.1 {
/*import net.minecraft.client.server.IntegratedServer;
*///? } else {
import net.minecraft.server.integrated.IntegratedServer;
//? }
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin {
    //? if >=26.1 {
    /*@Inject(method = "stopServer", at = @At("HEAD"))
    private void resetWarn(CallbackInfo ci) {
    *///? } else {
    @Inject(method = "stop", at = @At("HEAD"))
    private void resetWarn(boolean waitForShutdown, CallbackInfo ci) {
    //? }
        TT20.warned = false;
    }
}
