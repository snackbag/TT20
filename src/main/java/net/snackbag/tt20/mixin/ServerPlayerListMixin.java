package net.snackbag.tt20.mixin;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.snackbag.tt20.ModUpdater;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerList.class)
public class ServerPlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At("TAIL"))

    private void sendPlayerUpdateMessageIfCorrectPermissions(Connection connection, ServerPlayer player, CallbackInfo ci) {
        if (!TT20.config.automaticUpdater() || !ModUpdater.hasUpdate) return;

        if (Objects.requireNonNull(player.getServer()).getPlayerList().isOp(player.getGameProfile())) {
            player.sendSystemMessage(Component.literal(ModUpdater.updateMessage));
            player.sendSystemMessage(Component.literal("§oOnly operators can see this message"));
        }
    }
}
