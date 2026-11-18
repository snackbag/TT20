package net.snackbag.tt20.mixin.item;

import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ServerItemCooldowns;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemCooldowns.class)
public class ItemCooldownsMixin {
    //? if >=1.21.11 {
    @ModifyVariable(method = "addCooldown(Lnet/minecraft/resources/Identifier;I)V", at = @At("HEAD"), argsOnly = true)
    //?} elif >=1.21.2 {
    /*@ModifyVariable(method = "addCooldown(Lnet/minecraft/resources/ResourceLocation;I)V", at = @At("HEAD"), argsOnly = true)
    *///?} else {
    /*@ModifyVariable(method = "addCooldown(Lnet/minecraft/world/item/Item;I)V", at = @At("HEAD"), argsOnly = true)
    *///?}
    private int onCooldownStarted(int time) {
        if (!((Object) this instanceof ServerItemCooldowns)) return time;
        if (!TT20.config.enabled() || !TT20.config.itemCooldownAcceleration()) return time;

        return TPSUtil.tt20(time, true);
    }
}
