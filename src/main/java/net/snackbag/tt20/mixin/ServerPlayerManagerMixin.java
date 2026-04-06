package net.snackbag.tt20.mixin;

//? if >=26.1 {
/*import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
*///?} else {
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
//?}
//? if >1.20.1 && <26.1 {
/*import net.minecraft.server.network.ConnectedClientData;
*///?}
import net.snackbag.tt20.ModUpdater;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=26.1 {
/*@Mixin(PlayerList.class)
*///?} else {
@Mixin(PlayerManager.class)
//?}
public abstract class ServerPlayerManagerMixin {

    //? if >=26.1 {
    /*@Inject(method = "placeNewPlayer", at = @At("TAIL"))
    *///?} else {
    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    //?}
    //? if >=26.1 {
    /*private void sendPlayerUpdateMessageIfCorrectPermissions(Connection connection, ServerPlayer player, CommonListenerCookie cookie, CallbackInfo ci) {
    *///?} else if <=1.20.1 {
    private void sendPlayerUpdateMessageIfCorrectPermissions(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
    //?} else {
    /*private void sendPlayerUpdateMessageIfCorrectPermissions(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo ci) {
    *///?}
        if (!TT20.config.automaticUpdater() || !ModUpdater.hasUpdate) return;
        //? if >=26.1 {
        /*if (((ServerPlayerEntityMixin) player).getServer().getPlayerList().isOp(player.nameAndId())) {
        *///?} else if >=1.21.9 {
        /*if (((ServerPlayerEntityMixin) player).getServer().getPlayerManager().isOperator(player.getPlayerConfigEntry())) {
        *///?} else {
        if (player.getServer().getPlayerManager().isOperator(player.getGameProfile())) {
        //?}
            //? if >=26.1 {
            /*player.sendSystemMessage(Component.literal(ModUpdater.updateMessage));
            player.sendSystemMessage(Component.literal("§oOnly operators can see this message"));
            *///?} else {
            player.sendMessage(Text.of(ModUpdater.updateMessage));
            player.sendMessage(Text.of("§oOnly operators can see this message"));
            //?}
        }
    }
}
