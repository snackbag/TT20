package net.snackbag.tt20.mixin;

//? if >=1.19 {
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SculkShriekerBlockEntity.class)
public class SculkShriekerBlockEntityMixin {
    @ModifyConstant(method = "shriek", constant = @Constant(intValue = 90))
    private int accelerateWarningTimer(int original) {
        if (!TT20.config.enabled() || !TT20.config.shriekerAcceleration()) return original;
        return TPSUtil.tt20(original, true);
    }
}
//?} else {
/*import net.snackbag.tt20.ModUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModUpdater.class)
public class SculkShriekerBlockEntityMixin {
    @Inject(method = "check", at = @At("HEAD"))
    private static void onInitialize(CallbackInfo ci) {
        throw new RuntimeException("Tried to load SculkShriekerBlockEntityMixin on <1.19");
    }
}
*///?}
