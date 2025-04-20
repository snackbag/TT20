package net.snackbag.tt20;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.snackbag.tt20.util.TPSUtil;

public class MainCommand {
    public MainCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("tt20")
                        .then(Commands.literal("tps").executes(ctx -> executeTps(ctx, true)))
                        .then(Commands.literal("status").executes(MainCommand::executeStatus))
                        .then(Commands.literal("toggle")
                                .requires(src -> src.hasPermission(3))
                                .executes(MainCommand::executeToggle))
                        .then(Commands.literal("reload")
                                .requires(src -> src.hasPermission(2))
                                .executes(MainCommand::executeReload))
        );
    }

    private static int executeReload(CommandContext<CommandSourceStack> ctx) {
        var source = ctx.getSource();

        //Config.reload();
        source.sendSuccess(() -> Component.literal(/*"Reloaded config"*/"Did nothing!!!"), false);

        return 1;
    }

    private static int executeToggle(CommandContext<CommandSourceStack> ctx) {
        Config.ENABLED.set(!Config.ENABLED.get());
        Config.ENABLED.save();

        return 1;
    }

    private static int executeTps(CommandContext<CommandSourceStack> ctx, boolean missedTicks) {
        CommandSourceStack source = ctx.getSource();
        source.sendSuccess(() -> Component.literal(
                "§7TPS " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getTPS(), true) +
                        "§7 with average " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getAverageTPS(), true) +
                        "§7 accurate " + TPSUtil.colorizeTPS(TT20.TPS_CALCULATOR.getMostAccurateTPS(), true)
        ), false);

        if (missedTicks) {
            source.sendSuccess(() -> Component.literal(
                    "§8Missed ticks: §7" + TPSUtil.formatMissedTicks(TT20.TPS_CALCULATOR.getAllMissedTicks())
            ), false);
        }
        return 1;
    }

    private static int executeStatus(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        source.sendSuccess(() -> Component.literal(
                "§7TT20 enabled: " + (Config.ENABLED.get() ? "§aON" : "§cOFF")
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§7Block entity acceleration: " + (Config.BLOCK_ENTITY_ACCELERATION.get() ? "§aON" : "§cOFF")
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§7Block breaking acceleration: " + (Config.BLOCK_BREAKING_ACCELERATION.get() ? "§aON" : "§cOFF")
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§7Potion effect acceleration: " + (Config.POTION_EFFECT_ACCELERATION.get() ? "§aON" : "§cOFF")
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§7Fluid acceleration: " + (Config.FLUID_ACCELERATION.get() ? "§aON" : "§cOFF")
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§7Pickup acceleration: " + (Config.PICKUP_ACCELERATION.get() ? "§aON" : "§cOFF")
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§7Eating acceleration: " + (Config.EATING_ACCELERATION.get() ? "§aON" : "§cOFF")
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§7Portal acceleration: " + (Config.PORTAL_ACCELERATION.get() ? "§aON" : "§cOFF")
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§7Sleeping acceleration: " + (Config.SLEEPING_ACCELERATION.get() ? "§aON" : "§cOFF")
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§7Time acceleration: " + (Config.TIME_ACCELERATION.get() ? "§aON" : "§cOFF")
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§7Random tickspeed acceleration: " + (Config.RANDOM_TICKSPEED_ACCELERATION.get() ? "§aON" : "§cOFF")
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§7Singleplayer warning: " + (Config.SINGLEPLAYER_WARNING.get() ? "§aON" : "§cOFF")
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§7Server watchdog: " + (Config.SERVER_WATCHDOG.get() ? "§aON" : "§cOFF")
        ), false);

        executeTps(ctx, false);

        source.sendSuccess(() -> Component.literal(
                "\n§8Version: §7" + TT20.VERSION
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§8MSPT: §7" + TT20.TPS_CALCULATOR.getMSPT()
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§8Missed ticks: §7" + TPSUtil.formatMissedTicks(TT20.TPS_CALCULATOR.getAllMissedTicks())
        ), false);
        source.sendSuccess(() -> Component.literal(
                "§8Automatic updater: §7" + (Config.AUTOMATIC_UPDATER.get() ? "§aenabled" : "§cdisabled")
        ), false);

        return 1;
    }
}
