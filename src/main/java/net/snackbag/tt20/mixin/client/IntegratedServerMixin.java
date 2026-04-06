package net.snackbag.tt20.mixin.client;

//? if fabric {
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
//?}

//? if forge {
/*import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
*///?}

//? if neoforge {
/*import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
*///?}

import net.minecraft.client.server.IntegratedServer;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if fabric
@Environment(EnvType.CLIENT)

//? if forge || neoforge
//@OnlyIn(Dist.CLIENT)

@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin {
    @Inject(method = "halt", at = @At("HEAD"))
    private void resetWarn(boolean waitForShutdown, CallbackInfo ci) {
        TT20.warned = false;
    }
}
