package net.snackbag.tt20.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
//? if >=26.1 {
/*import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
*///? } else {
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
//? }
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.util.TPSUtil;

public class MainCommand {
    public static void register(
            //? if >=26.1 {
            /*CommandDispatcher<CommandSourceStack> dispatcher,
            CommandBuildContext registryAccess,
            Commands.CommandSelection environment) {
            *///? } else {
            CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess registryAccess,
            CommandManager.RegistrationEnvironment environment) {
            //? }
        dispatcher.register(
                //? if >=26.1 {
                /*Commands.literal("tt20").executes(MainCommand::executeMain)
                        .then(Commands.literal("tps").executes(MainCommand::executeTps))
                        .then(Commands.literal("status").executes(MainCommand::executeStatus))
                *///? } else {
                CommandManager.literal("tt20").executes(MainCommand::executeMain)
                        .then(CommandManager.literal("tps").executes(MainCommand::executeTps))
                        .then(CommandManager.literal("status").executes(MainCommand::executeStatus))
                //? }
                        //? if >=26.1 {
                        /*.then(Commands.literal("toggle").requires(Commands.hasPermission(Commands.LEVEL_ADMINS)).executes(MainCommand::executeToggle))
                        .then(Commands.literal("reload").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS)).executes(MainCommand::executeReload))
                        *///? } else if >=1.21.11 {
                        /*.then(CommandManager.literal("toggle").requires(CommandManager.requirePermissionLevel(CommandManager.ADMINS_CHECK)).executes(MainCommand::executeToggle))
                        .then(CommandManager.literal("reload").requires(CommandManager.requirePermissionLevel(CommandManager.GAMEMASTERS_CHECK)).executes(MainCommand::executeReload))
                        *///?} else {
                        .then(CommandManager.literal("toggle").requires(source -> source.hasPermissionLevel(3)).executes(MainCommand::executeToggle))
                        .then(CommandManager.literal("reload").requires(source -> source.hasPermissionLevel(2)).executes(MainCommand::executeReload))
                        //?}
        );
    }

    //? if >=26.1 {
    /*private static int executeStatus(CommandContext<CommandSourceStack> context) {
    *///? } else {
    private static int executeStatus(CommandContext<ServerCommandSource> context) {
    //? }
        var source = context.getSource();

        //? if >=26.1 {
        /*source.sendSystemMessage(Component.literal("§7TT20 enabled: " + (TT20.config.enabled() ? "§aON" : "§cOFF")));
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
        *///? } else {
        source.sendMessage(Text.literal("§7TT20 enabled: " + (TT20.config.enabled() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Block entity acceleration: " + (TT20.config.blockEntityAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Block breaking acceleration: " + (TT20.config.blockBreakingAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Potion effect acceleration: " + (TT20.config.potionEffectAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Fluid acceleration: " + (TT20.config.fluidAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Pickup acceleration: " + (TT20.config.pickupAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Eating acceleration: " + (TT20.config.eatingAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Portal acceleration: " + (TT20.config.portalAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Sleeping acceleration: " + (TT20.config.sleepingAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Time acceleration: " + (TT20.config.timeAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Random tickspeed acceleration: " + (TT20.config.randomTickSpeedAcceleration() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Singleplayer warning: " + (TT20.config.singlePlayerWarning() ? "§aON" : "§cOFF")));
        source.sendMessage(Text.literal("§7Server watchdog: " + (TT20.config.serverWatchdog() ? "§aON" : "§cOFF")));
        executeTps(context, false);
        source.sendMessage(Text.literal("\n§8Version: §7" + TT20.VERSION));
        source.sendMessage(Text.literal("§8MSPT: §7" + TT20.TPS_CALCULATOR.getMSPT()));
        source.sendMessage(Text.literal("§8Missed ticks: §7" + TPSUtil.formatMissedTicks(TT20.TPS_CALCULATOR.getAllMissedTicks())));
        source.sendMessage(Text.literal("§8Automatic updater: §7" + (TT20.config.automaticUpdater() ? "§aenabled" : "§cdisabled")));
        //? }

        return 1;
    }

    //? if >=26.1 {
    /*private static int executeReload(CommandContext<CommandSourceStack> context) {
    *///? } else {
    private static int executeReload(CommandContext<ServerCommandSource> context) {
    //? }
        var source = context.getSource();

        TT20.config.reload();
        //? if >=26.1 {
        /*source.sendSystemMessage(Component.literal("Reloaded config"));
        *///? } else {
        source.sendMessage(Text.literal("Reloaded config"));
        //? }
        TT20.blockEntityMaskConfig.reload();
        //? if >=26.1 {
        /*source.sendSystemMessage(Component.literal("Reloaded block entity mask config"));
        *///? } else {
        source.sendMessage(Text.literal("Reloaded block entity mask config"));
        //? }
        return 1;
    }

    //? if >=26.1 {
    /*private static int executeTps(CommandContext<CommandSourceStack> context) {
    *///? } else {
    private static int executeTps(CommandContext<ServerCommandSource> context) {
    //? }
        return executeTps(context, true);
    }

    //? if >=26.1 {
    /*private static int executeTps(CommandContext<CommandSourceStack> context, boolean missedTicks) {
    *///? } else {
    private static int executeTps(CommandContext<ServerCommandSource> context, boolean missedTicks) {
    //? }
        var source = context.getSource();

        //? if >=26.1 {
        /*source.sendSystemMessage(Component.literal(
        *///? } else {
        source.sendMessage(Text.literal(
        //? }
                "§7TPS " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getTPS(), true) +
                        "§7 with average " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getAverageTPS(), true) +
                        "§7 accurate " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getMostAccurateTPS(), true)
        ));

        //? if >=26.1 {
        /*if (missedTicks) source.sendSystemMessage(Component.literal("§8Missed ticks: §7" + TPSUtil.formatMissedTicks(TT20.TPS_CALCULATOR.getAllMissedTicks())));
        *///? } else {
        if (missedTicks) source.sendMessage(Text.literal("§8Missed ticks: §7" + TPSUtil.formatMissedTicks(TT20.TPS_CALCULATOR.getAllMissedTicks())));
        //? }

        return 1;
    }

    //? if >=26.1 {
    /*private static int executeToggle(CommandContext<CommandSourceStack> context) {
    *///? } else {
    private static int executeToggle(CommandContext<ServerCommandSource> context) {
    //? }
        var source = context.getSource();

        TT20.config.enabled(!TT20.config.enabled());
        TT20.config.save();

        String enabledText = TT20.config.enabled() ? "enabled" : "disabled";

        //? if >=26.1 {
        /*source.sendSystemMessage(Component.literal("TT20 is now " + enabledText));
        *///? } else {
        source.sendMessage(Text.literal("TT20 is now " + enabledText));
        //? }

        return 1;
    }


    //? if >=26.1 {
    /*private static int executeMain(CommandContext<CommandSourceStack> context) {
    *///? } else {
    private static int executeMain(CommandContext<ServerCommandSource> context) {
    //? }
        var source = context.getSource();

        //? if >=26.1 {
        /*source.sendSystemMessage(Component.literal("Running TT20 version " + TT20.VERSION));
        source.sendSystemMessage(Component.literal("Enabled: " + TT20.config.enabled()));
        *///? } else {
        source.sendMessage(Text.literal("Running TT20 version " + TT20.VERSION));
        source.sendMessage(Text.literal("Enabled: " + TT20.config.enabled()));
        //? }

        return 1;
    }
}
