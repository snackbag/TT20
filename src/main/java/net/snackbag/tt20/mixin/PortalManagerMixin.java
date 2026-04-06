package net.snackbag.tt20.mixin;

//? if >=26.1 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PortalProcessor;
import net.snackbag.tt20.TT20;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(PortalProcessor.class)
public abstract class PortalManagerMixin {
    @Shadow private int portalTime;

    @ModifyExpressionValue(method = "processPortalTeleportation", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/PortalProcessor;portalTime:I", opcode = Opcodes.GETFIELD))
    private int runMissedTicks(int original, @Local ServerLevel world) {
        if (!TT20.config.enabled() || !TT20.config.portalAcceleration()) return original;
        if (world.isClientSide()) return original;

        portalTime = portalTime + TT20.TPS_CALCULATOR.applicableMissedTicks();
        return portalTime;
    }
}
*///?} else if >=1.21 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.world.ServerWorld;
        return ticksInPortal;
    }
}
*///?} else {

import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TT20.class)
public class PortalManagerMixin {
    @Inject(method = "onInitialize", at = @At("HEAD"))
    public void onInitialize(CallbackInfo ci) {
        throw new RuntimeException("Tried to load PortalManagerMixin on <1.21");
    }
}
//?}