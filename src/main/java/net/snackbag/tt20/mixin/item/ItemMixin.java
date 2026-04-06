package net.snackbag.tt20.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.Item;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public abstract class ItemMixin {
    @ModifyReturnValue(method = "getUseDuration", at = @At("RETURN"))
    private int onGetMaxUseTime(int original) {
        if (!TT20.config.enabled() || !TT20.config.eatingAcceleration() || original == 0) return original;
        return TPSUtil.tt20(original, true);
    }
}
