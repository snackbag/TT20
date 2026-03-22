package net.snackbag.tt20.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
//? if >=26.1 {
/*import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
*///?} else {
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
//?}
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=26.1 {
/*@Mixin(Player.class)
*///?} else {
@Mixin(PlayerEntity.class)
//?}
public abstract class PlayerEntityMixin {
    //? if <=1.20.5 {
    @ModifyReturnValue(method = "getMaxNetherPortalTime", at = @At("RETURN"))
    private int netherPortalTimeTT20(int original) {
        if (!TT20.config.enabled() || !TT20.config.portalAcceleration()) return original;
        if (((Entity)(Object)this).getWorld().isClient()) return original;
        if (original == 1) return original;

        return TPSUtil.tt20(original, false);
    }
    //?}

    //? if >=26.1 {
    /*@ModifyExpressionValue(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;sleepCounter:I", opcode = Opcodes.GETFIELD))
    *///?} else {
    @ModifyExpressionValue(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;sleepTimer:I", opcode = Opcodes.GETFIELD))
    //?}
    private int tickTT20(int original) {
        if (!TT20.config.enabled() || !TT20.config.sleepingAcceleration()) return original;
        //? if >=26.1 {
        /*if (((Entity)(Object)this).level().isClientSide()) return original;
        *///?} else if >=1.21.9 {
        /*if (((Entity)(Object)this).getEntityWorld().isClient()) return original;
        *///?} else {
        if (((Entity)(Object)this).getWorld().isClient()) return original;
        //?}
        return original + TT20.TPS_CALCULATOR.applicableMissedTicks();
    }
}
