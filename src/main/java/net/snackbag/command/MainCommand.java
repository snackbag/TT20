package net.snackbag.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.snackbag.TT20;

public class MainCommand {
    public static void register(
            CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess registryAccess,
            CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(
                CommandManager.literal("tt20").executes(MainCommand::executeMain)
                        .then(CommandManager.literal("toggle").executes(MainCommand::executeToggle))
                        .then(CommandManager.literal("tps").executes(MainCommand::tps))
        );
    }

    private static int tps(CommandContext<ServerCommandSource> context) {
        return 1;
    }

    private static int executeToggle(CommandContext<ServerCommandSource> context) {
        return 1;
    }


    private static int executeMain(CommandContext<ServerCommandSource> context) {
        var source = context.getSource();

        source.sendMessage(Text.literal("Running TT20 version " + TT20.VERSION));

        return 1;
    }
}
