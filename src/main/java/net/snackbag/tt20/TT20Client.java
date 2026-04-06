package net.snackbag.tt20;

//? if fabric
import net.fabricmc.api.ClientModInitializer;

//? if forge {
/*import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
*///?}

//? if neoforge {
/*import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
*///?}

//? if neoforge && <1.20.5
//import net.neoforged.fml.common.Mod;

//? if neoforge && >=1.20.5 <1.21.9 {
/*import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
*///?}

//? if forge || (neoforge && <1.20.5)
//@Mod.EventBusSubscriber(modid = TT20.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

//? if neoforge && >=1.20.5 <1.21.9
//@EventBusSubscriber(modid = TT20.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)

public class TT20Client
        //? if fabric
        implements ClientModInitializer

    {

    //? if fabric
    @Override public void onInitializeClient()

    //? if neoforge
    //public TT20Client(IEventBus modBus)

    //? if forge
    //public TT20Client()

    {
        TT20.LOGGER.warn("******* WARNING *******");
        TT20.LOGGER.warn("(TT20) Mod is running on a client instead of server, this is not recommended.");
    }
}