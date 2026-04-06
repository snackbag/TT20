package net.snackbag.tt20.mixin;


//? if >1.20.1
//import net.minecraft.server.network.CommonListenerCookie;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.snackbag.tt20.ModUpdater;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.mixin.accessor.ServerPlayerAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public abstract class ServerPlayerManagerMixin {

    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    //? if >1.20.1 {
    /*private void sendPlayerUpdateMessageIfCorrectPermissions(Connection connection, ServerPlayer player, CommonListenerCookie cookie, CallbackInfo ci) {
    *///?} else {
    private void sendPlayerUpdateMessageIfCorrectPermissions(Connection netManager, ServerPlayer player, CallbackInfo ci) {
    //?}
        if (!TT20.config.automaticUpdater() || !ModUpdater.hasUpdate) return;
        //? if >=1.21.9 {
        /*if (((ServerPlayerAccessor) player).getServer().getPlayerList().isOp(player.nameAndId())) {
        *///?} else {
        if (player.getServer().getPlayerList().isOp(player.getGameProfile())) {
        //?}
            player.sendSystemMessage(Component.literal(ModUpdater.updateMessage));
            player.sendSystemMessage(Component.literal("§oOnly operators can see this message"));
        }
    }
}
