package net.snackbag.tt20.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

//? <=1.18.2
//import net.minecraft.network.chat.TextComponent;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;

import static net.snackbag.tt20.TT20.literal;
import static net.snackbag.tt20.TT20.sendMessage;

public class MainCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("tt20").executes(MainCommand::executeMain)
                        .then(Commands.literal("tps").executes(MainCommand::executeTps))
                        .then(Commands.literal("status").executes(MainCommand::executeStatus))
                        //? if (>=1.21.11) {
                        /*.then(Commands.literal("toggle").requires(Commands.hasPermission(Commands.LEVEL_ADMINS)).executes(MainCommand::executeToggle))
                        .then(Commands.literal("reload").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS)).executes(MainCommand::executeReload))
                        *///?} else {
                        .then(Commands.literal("toggle").requires(source -> source.hasPermission(3)).executes(MainCommand::executeToggle))
                        .then(Commands.literal("reload").requires(source -> source.hasPermission(2)).executes(MainCommand::executeReload))
                        //?}
        );
    }

    private static int executeStatus(CommandContext<CommandSourceStack> context) {
        sendMessage(context, literal("§7TT20 enabled: " + (TT20.config.enabled() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Block entity acceleration: " + (TT20.config.blockEntityAcceleration() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Block breaking acceleration: " + (TT20.config.blockBreakingAcceleration() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Potion effect acceleration: " + (TT20.config.potionEffectAcceleration() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Fluid acceleration: " + (TT20.config.fluidAcceleration() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Pickup acceleration: " + (TT20.config.pickupAcceleration() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Eating acceleration: " + (TT20.config.eatingAcceleration() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Portal acceleration: " + (TT20.config.portalAcceleration() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Sleeping acceleration: " + (TT20.config.sleepingAcceleration() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Time acceleration: " + (TT20.config.timeAcceleration() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Bow pull acceleration: " + (TT20.config.bowAcceleration() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Crossbow pull acceleration: " + (TT20.config.crossbowAcceleration() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Random tickspeed acceleration: " + (TT20.config.randomTickSpeedAcceleration() ? "§aON" : "§cOFF")));
        sendMessage(context, literal("§7Server watchdog: " + (TT20.config.serverWatchdog() ? "§aON" : "§cOFF")));
        executeTps(context, false);
        sendMessage(context, literal("\n§8Version: §7" + TT20.VERSION));
        sendMessage(context, literal("§8MSPT: §7" + TT20.TPS_CALCULATOR.getMSPT()));
        sendMessage(context, literal("§8Missed ticks: §7" + TPSUtil.formatMissedTicks(TT20.TPS_CALCULATOR.getAllMissedTicks())));
        sendMessage(context, literal("§8Automatic updater: §7" + (TT20.config.automaticUpdater() ? "§aenabled" : "§cdisabled")));

        return 1;
    }

    private static int executeReload(CommandContext<CommandSourceStack> context) {
        TT20.config.reload();
        sendMessage(context, literal("Reloaded config"));
        TT20.blockEntityMaskConfig.reload();
        sendMessage(context, literal("Reloaded block entity mask config"));

        return 1;
    }

    private static int executeTps(CommandContext<CommandSourceStack> context) {
        return executeTps(context, true);
    }

    private static int executeTps(CommandContext<CommandSourceStack> context, boolean missedTicks) {
        sendMessage(context, literal(
                "§7TPS " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getTPS(), true) +
                        "§7 with average " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getAverageTPS(), true) +
                        "§7 accurate " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getMostAccurateTPS(), true)
        ));

        if (missedTicks) sendMessage(context, literal("§8Missed ticks: §7" + TPSUtil.formatMissedTicks(TT20.TPS_CALCULATOR.getAllMissedTicks())));

        return 1;
    }

    private static int executeToggle(CommandContext<CommandSourceStack> context) {
        TT20.config.enabled(!TT20.config.enabled());
        TT20.config.save();

        String enabledText = TT20.config.enabled() ? "enabled" : "disabled";
        sendMessage(context, literal("TT20 is now " + enabledText));

        return 1;
    }


    private static int executeMain(CommandContext<CommandSourceStack> context) {
        sendMessage(context, literal("Running TT20 version " + TT20.VERSION));
        sendMessage(context, literal("Enabled: " + TT20.config.enabled()));

        return 1;
    }
}
