package net.snackbag.tt20;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

//? if fabric
import net.fabricmc.api.ModInitializer;

//? if forge
//import net.minecraftforge.fml.common.Mod;

//? if neoforge {
/*import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
*///?}

//? if >=1.21.11 {
/*import net.minecraft.resources.Identifier;
*///?} else {
import net.minecraft.resources.ResourceLocation;
//?}

//? 1.18.2
//import net.minecraft.network.chat.TextComponent;

import net.snackbag.tt20.config.BlockEntityMaskConfig;
import net.snackbag.tt20.config.MainConfig;
import net.snackbag.tt20.util.TPSCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static net.snackbag.tt20.TT20.MOD_ID;


//? if forge || neoforge
//@Mod(MOD_ID)

@SuppressWarnings("removal")
public class TT20
		//? if fabric
		implements ModInitializer

		{
	public static final String MOD_ID = "tt20";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final String VERSION = /*$ mod_version */"0.7.2";
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

	//? >=1.21.11 {
	/*public static Identifier id(String namespace, String path) {
	*///?} else {
	public static ResourceLocation id(String namespace, String path) {
	//?}
		//? if >=1.21.11 {
		/*return Identifier.tryBuild(namespace, path);
		 *///?} else if >1.18.2 {
		return ResourceLocation.tryBuild(namespace, path);
		 //?} else {
		/*return new ResourceLocation(namespace, path);
		*///?}
	}

	public static void sendMessage(CommandContext<CommandSourceStack> context, Component message) {
        var source = context.getSource();

        //? if >1.18.2 {
        source.sendSystemMessage(message);
        //?} else {
        /*source.sendSuccess(message, false);
        *///?}
    }

	public static void sendMessage(ServerPlayer player, Component message) {
		//? if >1.18.2 {
		player.sendSystemMessage(message);
		 //?} else {
		/*player.sendMessage(message, UUID.randomUUID());
		*///?}
	}

	public static Component literal(String string) {
        //? if >1.18.2 {
        return Component.literal(string);
         //?} else {
        /*return new TextComponent(string);
        *///?}
    }
}