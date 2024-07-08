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
                        .then(CommandManager.literal("tps").executes(MainCommand::executeTps))
                        .then(CommandManager.literal("status").executes(MainCommand::executeStatus))
                        .then(CommandManager.literal("toggle").requires(source -> source.hasPermissionLevel(3)).executes(MainCommand::executeToggle))
                        .then(CommandManager.literal("reload").requires(source -> source.hasPermissionLevel(2)).executes(MainCommand::executeReload))
        );
    }

    private static int executeStatus(CommandContext<ServerCommandSource> context) {
        var source = context.getSource();

        source.sendMessage(Text.literal("§7TT20 enabled: " + (TT20.config.enabled() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Block entity acceleration: " + (TT20.config.blockEntityAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Potion effect acceleration: " + (TT20.config.potionEffectAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Fluid acceleration: " + (TT20.config.fluidAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Pickup acceleration: " + (TT20.config.pickupAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Eating acceleration: " + (TT20.config.eatingAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Server watchdog: " + (TT20.config.serverWatchdog() ? "§aON" : "§cOFF")));
        executeTps(context, false);
        source.sendMessage(Text.literal("\n§8Version: §7" + TT20.VERSION));
        source.sendMessage(Text.literal("§8MSPT: §7" + TT20.TPS_CALCULATOR.getMSPT()));
        source.sendMessage(Text.literal("§8Missed ticks: §7" + TT20.TPS_CALCULATOR.getAllMissedTicks()));

        return 1;
    }

    private static int executeReload(CommandContext<ServerCommandSource> context) {
        var source = context.getSource();

        TT20.config.reload();
        source.sendMessage(Text.literal("Reloaded config"));
        TT20.blockEntityMaskConfig.reload();
        source.sendMessage(Text.literal("Reloaded block entity mask config"));

        return 1;
    }

    private static int executeTps(CommandContext<ServerCommandSource> context) {
        return executeTps(context, true);
    }

    private static int executeTps(CommandContext<ServerCommandSource> context, boolean missedTicks) {
        var source = context.getSource();

        source.sendMessage(Text.literal(
                "§7TPS " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getTPS(), true) +
                        "§7 with average " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getAverageTPS(), true) +
                        "§7 accurate " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getMostAccurateTPS(), true)
        ));

        if (missedTicks) source.sendMessage(Text.literal("§8Missed ticks: §7" + TT20.TPS_CALCULATOR.getAllMissedTicks()));

        return 1;
    }

    private static int executeToggle(CommandContext<ServerCommandSource> context) {
        var source = context.getSource();

        TT20.config.enabled(!TT20.config.enabled());
        TT20.config.save();

        String enabledText = TT20.config.enabled() ? "enabled" : "disabled";
        source.sendMessage(Text.literal("TT20 is now " + enabledText));

        return 1;
    }


    private static int executeMain(CommandContext<ServerCommandSource> context) {
        var source = context.getSource();

        source.sendMessage(Text.literal("Running TT20 version " + TT20.VERSION));
        source.sendMessage(Text.literal("Enabled: " + TT20.config.enabled()));

        return 1;
    }
}
