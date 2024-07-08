package net.snackbag.tt20.mixin;

//? if >=1.21 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.dimension.PortalManager;
import net.snackbag.tt20.TT20;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(PortalManager.class)
public abstract class PortalManagerMixin {
    @Shadow private int ticksInPortal;

    @ModifyExpressionValue(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/dimension/PortalManager;ticksInPortal:I", opcode = Opcodes.GETFIELD))
    private int runMissedTicks(int original) {
        if (!TT20.config.enabled() || !TT20.config.portalAcceleration()) return original;

        ticksInPortal = ticksInPortal + TT20.TPS_CALCULATOR.applicableMissedTicks();
        return ticksInPortal;
    }
}
*///?}