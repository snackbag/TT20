package net.snackbag.tt20.mixin.client;

//? if 1.19.2
//import com.mojang.blaze3d.vertex.PoseStack;

//? if >=1.20.1
import net.minecraft.client.gui.GuiGraphics;

//? if >=1.21.11
//import net.minecraft.client.gui.Font;

//? if fabric {
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
//?}

import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if fabric
@Environment(EnvType.CLIENT)

@Mixin(ChatComponent.class)
public abstract class ChatHudMixin {
    @Shadow public abstract void addMessage(Component message);
    //? if >=1.21.11 {
    /*@Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Font;IIIZZ)V", at = @At("HEAD"))
    *///?} else {
    @Inject(method = "render", at = @At("HEAD"))
    //?}

    //? if >=1.21.11 {
    /*private void onPlayerConnectWarn(GuiGraphics context, Font textRenderer, int currentTick, int mouseX, int mouseY, boolean interactable, boolean bl, CallbackInfo ci) {
    *///?} elif >=1.20.5 && <1.21.11 {
    /*private void onPlayerConnectWarn(GuiGraphics guiGraphics, int tickCount, int mouseX, int mouseY, boolean focused, CallbackInfo ci) {
    *///?} elif >=1.20.1 && <1.20.5 {
    private void onPlayerConnectWarn(GuiGraphics context, int currentTick, int mouseX, int mouseY, CallbackInfo ci) {
    //?} elif 1.19.2 {
    /*private void onPlayerConnectWarn(PoseStack poseStack, int i, CallbackInfo ci) {
    *///?}
        if (TT20.warned || !TT20.config.singlePlayerWarning()) return;
        addMessage(Component.literal("§c§lCritical incompatibilities found!\n\n§c§6TT20 §cis not stable on singleplayer and you may find yourself having unwanted side effects. You can disable each feature in the config if it gets too annoying."));
        TT20.warned = true;
    }
}
