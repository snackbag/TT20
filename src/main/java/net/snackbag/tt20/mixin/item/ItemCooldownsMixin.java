package net.snackbag.tt20.mixin.item;

import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ServerItemCooldowns;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemCooldowns.class)
public class ItemCooldownsMixin {
    @Shadow
    private int tickCount;

    //? if >=26.1.1 {
    @ModifyArg(method = "addCooldown", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/resources/Identifier;I)V"), index = 1)
    //?} else {
    /*@ModifyArg(method = "addCooldown", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns$CooldownInstance;<init>(II)V"), index = 1)
    *///?}
    private int onCooldownStarted(int endTime) {
        if (!((Object) this instanceof ServerItemCooldowns)) return endTime;
        if (!TT20.config.enabled() || !TT20.config.itemCooldownAcceleration()) return endTime;

        int ticks = endTime - this.tickCount;
        return this.tickCount + TPSUtil.tt20(ticks, true);
    }
}
