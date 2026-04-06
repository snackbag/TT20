package net.snackbag.tt20;

//? if fabric
import net.fabricmc.api.ClientModInitializer;

//? if forge
//import net.minecraftforge.fml.common.Mod;

//? if neoforge {
/*import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
*///?}

//? if forge || neoforge
//@Mod.EventBusSubscriber(modid = TT20.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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