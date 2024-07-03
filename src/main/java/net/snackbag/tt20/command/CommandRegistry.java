package net.snackbag.tt20.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandRegistry {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(MainCommand::register);
    }
}
