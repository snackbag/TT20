package net.snackbag.tt20.mixin;

import net.minecraft.world.entity.item.PrimedTnt;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PrimedTnt.class)
public class PrimedTntMixin {
    @ModifyVariable(
            method = "tick",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    private int modifyFuseDecrement(int original) {
        return TT20.config.tntAcceleration() ? TPSUtil.tt20(original, true) : original;
    }
}