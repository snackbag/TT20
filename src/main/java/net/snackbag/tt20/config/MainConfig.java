package net.snackbag.tt20.config;

public class MainConfig extends JSONConfiguration {
    private boolean enabled;
    private boolean blockEntityAcceleration;
    private boolean serverWatchdog;

    public MainConfig() {
        super("config.json");

        putIfEmpty("enabled", true);
        putIfEmpty("block-entity-acceleration", false);
        putIfEmpty("server-watchdog", true);

        save();
    }

    @Override
    public void reload() {
        super.reload();
        this.enabled = getAsBoolean("enabled");
        this.blockEntityAcceleration = getAsBoolean("block-entity-acceleration");
        this.serverWatchdog = getAsBoolean("block-entity-acceleration");
    }

    public void enabled(boolean enabled) {
        put("enabled", enabled);
    }

    public boolean enabled() {
        return enabled;
    }

    public void serverWatchdog(boolean enabled) {
        put("server-watchdog", enabled);
    }

    public boolean serverWatchdog() {
        return serverWatchdog;
    }

    public void blockEntityAcceleration(boolean enabled) {
        put("block-entity-acceleration", enabled);
    }

    public boolean blockEntityAcceleration() {
        return blockEntityAcceleration;
    }
}
