package net.snackbag.tt20.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.Item;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public class ItemMixin {
    @ModifyReturnValue(method = "getMaxUseTime", at = @At("RETURN"))
    private int onGetMaxUseTime(int original) {
        if (!TT20.config.enabled() || original == 0) return original;
        return TPSUtil.tt20(original);
    }
}
