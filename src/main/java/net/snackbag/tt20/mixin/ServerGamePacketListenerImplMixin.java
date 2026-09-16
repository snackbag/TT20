package net.snackbag.tt20.mixin;

import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Inject(
            //? >=26.3 {
            /*method = "handlePlayerPositionChange",
            *///?} else {
            method = "handleMovePlayer",
            //?}
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;teleport(DDDFF)V",
                    //? >=26.3 {
                    /*ordinal = 1
                    *///?} else {
                    ordinal = 2
                    //?}
            ),
            cancellable = true
    )
    //? >=26.3 {
    /*private void onMovedTooQuickly(double requestedX, double requestedY, double requestedZ, float requestedYRot, float requestedXRot, boolean isOnGround, boolean horizontalCollision, CallbackInfo ci) {
    *///?} else {
    private void onMovedTooQuickly(ServerboundMovePlayerPacket packet, CallbackInfo ci) {
     //?}
        if (!TT20.config.vanillaLagback()) ci.cancel();
    }
}