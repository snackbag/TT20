package net.snackbag.tt20.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;

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
        var source = context.getSource();

        source.sendMessage(Text.literal("§7TPS: " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getTPS())));

        return 1;
    }

    private static int executeToggle(CommandContext<ServerCommandSource> context) {
        var source = context.getSource();

        TT20.enabled = !TT20.enabled;
        String enabledText = TT20.enabled ? "enabled" : "disabled";
        source.sendMessage(Text.literal("TT20 is now " + enabledText));

        return 1;
    }


    private static int executeMain(CommandContext<ServerCommandSource> context) {
        var source = context.getSource();

        source.sendMessage(Text.literal("Running TT20 version " + TT20.VERSION));
        source.sendMessage(Text.literal("Enabled: " + TT20.enabled));

        return 1;
    }
}