package net.snackbag.tt20;

import net.fabricmc.api.ClientModInitializer;

public class TT20Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TT20.LOGGER.warn("******* WARNING *******");
        TT20.LOGGER.warn("(TT20) Mod is running on a client instead of server, this is not recommended and will lead to errors.");
    }
}
