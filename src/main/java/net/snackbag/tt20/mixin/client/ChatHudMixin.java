package net.snackbag.tt20.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
//? if >=26.1 {
/*import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
*///? } else if >=1.20.1 {
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
//?} else {
/*import net.minecraft.client.util.math.MatrixStack;
*///?}
//? if <26.1 {
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
//? }
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
//? if >=26.1 {
/*@Mixin(ChatComponent.class)
*///?} else {
@Mixin(ChatHud.class)
//?}
public abstract class ChatHudMixin {
    //? if >=26.1 {
    /*@Shadow public abstract void addClientSystemMessage(Component message);
    *///?} else {
    @Shadow public abstract void addMessage(Text message);
    //?}
    //? if >=26.1 {
    /*@Inject(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/gui/Font;IIILnet/minecraft/client/gui/components/ChatComponent$DisplayMode;Z)V", at = @At("HEAD"))
    *///?} else if >=1.21.11 {
    /*@Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/font/TextRenderer;IIIZZ)V", at = @At("HEAD"))
    *///?} else {
    @Inject(method = "render", at = @At("HEAD"))
    //?}

    //?if >= 26.1 {
    /*private void onPlayerConnectWarn(GuiGraphicsExtractor graphics, Font font, int ticks, int mouseX, int mouseY, ChatComponent.DisplayMode displayMode, boolean changeCursorOnInsertions, CallbackInfo ci) {
    *///?} else if >=1.21.11 {
    /*private void onPlayerConnectWarn(DrawContext context, TextRenderer textRenderer, int currentTick, int mouseX, int mouseY, boolean interactable, boolean bl, CallbackInfo ci) {
    *///?} elif >=1.20.5 && <1.21.11 {
    /*private void onPlayerConnectWarn(DrawContext context, int currentTick, int mouseX, int mouseY, boolean focused, CallbackInfo ci) {
    *///?} elif >=1.20.1 && <1.20.5 {
    private void onPlayerConnectWarn(DrawContext context, int currentTick, int mouseX, int mouseY, CallbackInfo ci) {
    //?} elif =1.19.2 {
    /*private void onPlayerConnectWarn(MatrixStack matrices, int currentTick, CallbackInfo ci) {
    *///?}
        if (TT20.warned || !TT20.config.singlePlayerWarning()) return;
        //? if >=26.1 {
        /*addClientSystemMessage(Component.literal("§c§lCritical incompatibilities found!\n\n§c§6TT20 §cis not stable on singleplayer and you may find yourself having unwanted side effects. You can disable each feature in the config if it gets too annoying."));
        *///?} else {
        addMessage(Text.literal("§c§lCritical incompatibilities found!\n\n§c§6TT20 §cis not stable on singleplayer and you may find yourself having unwanted side effects. You can disable each feature in the config if it gets too annoying."));
        //?}
        TT20.warned = true;
    }
}
