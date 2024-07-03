package net.snackbag;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.snackbag.util.TPSCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TT20 implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("tt20");
	public static final String VERSION = "0.1.0";
	public static final TPSCalculator TPS_CALCULATOR = new TPSCalculator();

	@Override
	public void onInitialize() {
		LOGGER.info("Starting TT20...");
	}
}