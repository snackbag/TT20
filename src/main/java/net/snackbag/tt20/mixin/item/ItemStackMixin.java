package net.snackbag.tt20.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @ModifyReturnValue(method = "getUseDuration", at = @At(value = "RETURN"))
    private int onGetMaxUseTime(int original, @Local(argsOnly = true) LivingEntity livingEntity) {
        //? if >=1.20.1 {
        if (livingEntity.level().isClientSide()) return original;
        //?} else {
        /*if (livingEntity.level.isClientSide) return original;
        *///?}

        if (!TT20.config.enabled() || !TT20.config.eatingAcceleration() || original == 0) return original;
        //? if >=26.1 {
        if (livingEntity.getActiveItem().is(Items.BOW)) return original;
        //?} else {
        /*if (livingEntity.getUseItem().is(Items.BOW)) return original;
        *///?}
        return TPSUtil.tt20(original, true);
    }
}
