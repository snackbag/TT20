package net.snackbag.tt20.mixin.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.snackbag.tt20.TT20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(ChatComponent.class)
public abstract class ChatComponentMixin {
    @Shadow
    public abstract void addMessage(Component message);
    @Inject(method = "render", at = @At("HEAD"))
    private void onPlayerConnectWarn(GuiGraphics context, int currentTick, int mouseX, int mouseY, CallbackInfo ci) {
        if (TT20.warned || !TT20.config.singlePlayerWarning()) return;
        addMessage(Component.literal("§c§lCritical incompatibilities found!\n\n§c§6TT20 §cis not stable on singleplayer and you may find yourself having unwanted side effects. You can disable each feature in the config if it gets too annoying."));
        TT20.warned = true;
    }
}
