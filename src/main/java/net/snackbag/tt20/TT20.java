package net.snackbag.tt20;

import net.fabricmc.api.ModInitializer;

import net.snackbag.tt20.command.CommandRegistry;
import net.snackbag.tt20.config.TT20Config;
import net.snackbag.tt20.util.TPSCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class TT20 implements ModInitializer {
    public static final String MOD_ID = "tt20";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final String VERSION = "0.7.1";
	public static final TPSCalculator TPS_CALCULATOR = new TPSCalculator();
    public static final TT20Config Config = new TT20Config();

	public static boolean warned = false;

	@Override
	public void onInitialize() {
		LOGGER.info("Starting TT20...");

		CompletableFuture.runAsync(() -> {
			try {
				ModUpdater.check();
			} catch (RuntimeException e) {
				LOGGER.error("Failed to check for updates.");
				e.printStackTrace();
			}
		});

		CommandRegistry.registerCommands();

        if (Config.reload()) {
            LOGGER.info("Reloaded config.");
        } else {
            LOGGER.info("No config changes.");
        }
	}
}