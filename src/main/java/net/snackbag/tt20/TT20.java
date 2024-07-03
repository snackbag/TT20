package net.snackbag.tt20;

import net.fabricmc.api.ModInitializer;

import net.snackbag.tt20.command.CommandRegistry;
import net.snackbag.tt20.config.MainConfig;
import net.snackbag.tt20.util.TPSCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TT20 implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("tt20");
	public static final String VERSION = "0.1.0";
	public static final TPSCalculator TPS_CALCULATOR = new TPSCalculator();

	public static final MainConfig config = new MainConfig();

	@Override
	public void onInitialize() {
		LOGGER.info("Starting TT20...");
		CommandRegistry.registerCommands();
	}
}