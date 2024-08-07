package net.snackbag.tt20.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.snackbag.tt20.ModUpdater;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class ServerPlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    private void sendPlayerUpdateMessageIfCorrectPermissions(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        if (!TT20.config.automaticUpdater() || !ModUpdater.hasUpdate) return;

        if (player.getServer().getPlayerManager().isOperator(player.getGameProfile())) {
            player.sendMessage(Text.of(ModUpdater.updateMessage));
            player.sendMessage(Text.of("§oOnly operators can see this message"));
        }
    }
}
