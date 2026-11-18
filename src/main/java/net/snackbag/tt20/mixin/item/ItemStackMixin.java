package net.snackbag.tt20.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @ModifyReturnValue(method = "getUseDuration", at = @At(value = "RETURN"))
    //? if >1.20.5 {
    private int onGetMaxUseTime(int original, @Local(argsOnly = true) LivingEntity livingEntity) {
    //?} else {
    /*private int onGetMaxUseTime(int original) {
    *///?}
        //? if >1.20.5 {
        if (livingEntity.level().isClientSide()) return original;
        //?} else {
        /*if (!Thread.currentThread().getName().equals("Server thread")) return original; // Workaround to check side
        *///?}

        if (!TT20.config.enabled() || !TT20.config.eatingAcceleration() || original == 0) return original;
        if (this.getItem() == Items.BOW) return original;
        return TPSUtil.tt20(original, true);
    }
}
