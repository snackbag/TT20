package net.snackbag.tt20.mixin;

//? if >=1.19 {
import net.minecraft.world.entity.monster.warden.WardenAi;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WardenAi.class)
public class WardenAiMixin {
    @ModifyConstant(method = "setDigCooldown", constant = @Constant(longValue = 1200L))
    private static long accelerateDespawnTimer(long original) {
        if (!TT20.config.enabled() || !TT20.config.wardenAcceleration()) return original;
        return (long) TPSUtil.tt20((double) original, true);
    }
}
//?} else {
/*import net.snackbag.tt20.ModUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModUpdater.class)
public class WardenAiMixin {
    @Inject(method = "check", at = @At("HEAD"))
    private static void onInitialize(CallbackInfo ci) {
        throw new RuntimeException("Tried to load WardenAiMixin on <1.19");
    }
}
*///?}
