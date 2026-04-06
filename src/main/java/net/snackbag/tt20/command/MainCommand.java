package net.snackbag.tt20.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;

public class MainCommand {
    public static void register(
            CommandDispatcher<CommandSourceStack> dispatcher,
            CommandBuildContext registryAccess,
            Commands.CommandSelection environment) {
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
        var source = context.getSource();

        source.sendSystemMessage(Component.literal("§7TT20 enabled: " + (TT20.config.enabled() ? "§aON" : "§cOFF")));
        source.sendSystemMessage(Component.literal("§7Block entity acceleration: " + (TT20.config.blockEntityAcceleration() ? "§aON" : "§cOFF")));
        source.sendSystemMessage(Component.literal("§7Block breaking acceleration: " + (TT20.config.blockBreakingAcceleration() ? "§aON" : "§cOFF")));
        source.sendSystemMessage(Component.literal("§7Potion effect acceleration: " + (TT20.config.potionEffectAcceleration() ? "§aON" : "§cOFF")));
        source.sendSystemMessage(Component.literal("§7Fluid acceleration: " + (TT20.config.fluidAcceleration() ? "§aON" : "§cOFF")));
        source.sendSystemMessage(Component.literal("§7Pickup acceleration: " + (TT20.config.pickupAcceleration() ? "§aON" : "§cOFF")));
        source.sendSystemMessage(Component.literal("§7Eating acceleration: " + (TT20.config.eatingAcceleration() ? "§aON" : "§cOFF")));
        source.sendSystemMessage(Component.literal("§7Portal acceleration: " + (TT20.config.portalAcceleration() ? "§aON" : "§cOFF")));
        source.sendSystemMessage(Component.literal("§7Sleeping acceleration: " + (TT20.config.sleepingAcceleration() ? "§aON" : "§cOFF")));
        source.sendSystemMessage(Component.literal("§7Time acceleration: " + (TT20.config.timeAcceleration() ? "§aON" : "§cOFF")));
        source.sendSystemMessage(Component.literal("§7Random tickspeed acceleration: " + (TT20.config.randomTickSpeedAcceleration() ? "§aON" : "§cOFF")));
        source.sendSystemMessage(Component.literal("§7Singleplayer warning: " + (TT20.config.singlePlayerWarning() ? "§aON" : "§cOFF")));
        source.sendSystemMessage(Component.literal("§7Server watchdog: " + (TT20.config.serverWatchdog() ? "§aON" : "§cOFF")));
        executeTps(context, false);
        source.sendSystemMessage(Component.literal("\n§8Version: §7" + TT20.VERSION));
        source.sendSystemMessage(Component.literal("§8MSPT: §7" + TT20.TPS_CALCULATOR.getMSPT()));
        source.sendSystemMessage(Component.literal("§8Missed ticks: §7" + TPSUtil.formatMissedTicks(TT20.TPS_CALCULATOR.getAllMissedTicks())));
        source.sendSystemMessage(Component.literal("§8Automatic updater: §7" + (TT20.config.automaticUpdater() ? "§aenabled" : "§cdisabled")));

        return 1;
    }

    private static int executeReload(CommandContext<CommandSourceStack> context) {
        var source = context.getSource();

        TT20.config.reload();
        source.sendSystemMessage(Component.literal("Reloaded config"));
        TT20.blockEntityMaskConfig.reload();
        source.sendSystemMessage(Component.literal("Reloaded block entity mask config"));

        return 1;
    }

    private static int executeTps(CommandContext<CommandSourceStack> context) {
        return executeTps(context, true);
    }

    private static int executeTps(CommandContext<CommandSourceStack> context, boolean missedTicks) {
        var source = context.getSource();

        source.sendSystemMessage(Component.literal(
                "§7TPS " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getTPS(), true) +
                        "§7 with average " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getAverageTPS(), true) +
                        "§7 accurate " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getMostAccurateTPS(), true)
        ));

        if (missedTicks) source.sendSystemMessage(Component.literal("§8Missed ticks: §7" + TPSUtil.formatMissedTicks(TT20.TPS_CALCULATOR.getAllMissedTicks())));

        return 1;
    }

    private static int executeToggle(CommandContext<CommandSourceStack> context) {
        var source = context.getSource();

        TT20.config.enabled(!TT20.config.enabled());
        TT20.config.save();

        String enabledText = TT20.config.enabled() ? "enabled" : "disabled";
        source.sendSystemMessage(Component.literal("TT20 is now " + enabledText));

        return 1;
    }


    private static int executeMain(CommandContext<CommandSourceStack> context) {
        var source = context.getSource();

        source.sendSystemMessage(Component.literal("Running TT20 version " + TT20.VERSION));
        source.sendSystemMessage(Component.literal("Enabled: " + TT20.config.enabled()));

        return 1;
    }
}
