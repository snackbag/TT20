package net.snackbag.tt20.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.player.PlayerEntity;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @ModifyReturnValue(method = "getMaxNetherPortalTime", at = @At("RETURN"))
    private int netherPortalTimeTT20(int original) {
        if (!TT20.config.enabled()) return original;
        if (original == 1) return original;

        return TPSUtil.tt20(original, false);
    }
}
