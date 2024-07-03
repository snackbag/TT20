package net.snackbag.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandRegistry {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(MainCommand::register);
    }
}
