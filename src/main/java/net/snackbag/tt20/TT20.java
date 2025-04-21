package net.snackbag.tt20;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.command.ConfigCommand;
import net.snackbag.tt20.config.BlockEntityMaskConfig;
import net.snackbag.tt20.config.MainConfig;
import org.slf4j.Logger;

import net.snackbag.tt20.util.TPSCalculator;

@Mod(TT20.MODID)
public class TT20 {
    public static final String MODID = "tt20";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String VERSION = "0.7.1";
    public static final TPSCalculator TPS_CALCULATOR = new TPSCalculator();

    public static final MainConfig config = new MainConfig();
    public static final BlockEntityMaskConfig blockEntityMaskConfig = new BlockEntityMaskConfig();

    public TT20(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(TPS_CALCULATOR);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Starting TT20...");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            TT20.LOGGER.warn("******* WARNING *******");
            TT20.LOGGER.warn("(TT20) Mod is running on a client instead of server, this is not recommended and will lead to errors.");
        }
    }

    @Mod.EventBusSubscriber(modid = TT20.MODID)
    public static class CommandRegistrationHandler {
        @SubscribeEvent
        public static void onCommandsRegister(RegisterCommandsEvent event) {
            new MainCommand(event.getDispatcher());
            ConfigCommand.register(event.getDispatcher());
        }
    }

}
