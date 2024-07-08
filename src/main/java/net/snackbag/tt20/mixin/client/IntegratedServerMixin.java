package net.snackbag.tt20.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.integrated.IntegratedServer;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin {
    @Inject(method = "stop", at = @At("HEAD"))
    private void resetWarn(boolean waitForShutdown, CallbackInfo ci) {
        TT20.warned = false;
    }
}
