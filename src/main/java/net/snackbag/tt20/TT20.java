package net.snackbag.tt20;

//? if fabric
import net.fabricmc.api.ModInitializer;

//? if forge
//import net.minecraftforge.fml.common.Mod;

//? if neoforge {
/*import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
*///?}

import net.snackbag.tt20.config.BlockEntityMaskConfig;
import net.snackbag.tt20.config.MainConfig;
import net.snackbag.tt20.util.TPSCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

import static net.snackbag.tt20.TT20.MOD_ID;


//? if forge || neoforge
//@Mod(MOD_ID)
public class TT20
		//? if fabric
		implements ModInitializer

		{
	public static final String MOD_ID = "tt20";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final String VERSION = /*$ mod_version */"placeholder";
	public static final TPSCalculator TPS_CALCULATOR = new TPSCalculator();
	public static final MainConfig config = new MainConfig();
	public static final BlockEntityMaskConfig blockEntityMaskConfig = new BlockEntityMaskConfig();

	public static boolean warned = false;

	//? if fabric
	@Override public void onInitialize()

	//? if neoforge
	 //public TT20(IEventBus modBus) 

	//? if forge
	//public TT20()

	//? if paper
	/* @Override public void onEnable() */

	{
		LOGGER.info("Starting TT20...");

		CompletableFuture.runAsync(() -> {
			try {
				ModUpdater.check();
			} catch (RuntimeException e) {
				LOGGER.error("Failed to check for updates.");
				e.printStackTrace();
			}
		});
	}
}