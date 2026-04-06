package net.snackbag.tt20.mixin.world;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.level.ServerLevel;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @ModifyExpressionValue(
            method = "tickTime",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/WritableLevelData;getDayTime()J")
    )
    private long addMissingTicksToTime(long original) {
        IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
        boolean isLan = server != null && server.isPublished();

        //? if >=1.20.1 {
        boolean isPaused = Minecraft.getInstance().isSingleplayer() && Minecraft.getInstance().isPaused() && !isLan;
        //?} else {
        /*boolean isPaused = server.isSingleplayer() && Minecraft.getInstance().isPaused() && !isLan;
        *///?}

        if (!TT20.config.enabled() || !TT20.config.timeAcceleration() || isPaused) return original;
        return original + TT20.TPS_CALCULATOR.applicableMissedTicks();
    }
}
