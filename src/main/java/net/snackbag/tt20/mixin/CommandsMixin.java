package net.snackbag.tt20.mixin;

import com.mojang.brigadier.CommandDispatcher;
//? >1.19.2
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.snackbag.tt20.TT20Command;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Commands.class)
public class CommandsMixin {
    @Final
    @Shadow
    private CommandDispatcher<CommandSourceStack> dispatcher;

    @Inject(method = "<init>", at = @At("RETURN"))
    //? if >1.19.2 {
    private void tt20$commandRegister(Commands.CommandSelection selection, CommandBuildContext context, CallbackInfo ci) {
    //?} else {
    /*private void tt20$commandRegister(Commands.CommandSelection selection, CallbackInfo ci) {
    *///?}
        TT20Command.register(dispatcher);
    }
}
