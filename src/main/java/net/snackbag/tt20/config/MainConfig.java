package net.snackbag.tt20.config;

public class MainConfig extends JSONConfiguration {
    public MainConfig() {
        super("config.json");

        putIfEmpty("enabled", true);
        putIfEmpty("server-watchdog", true);

        save();
    }

    public void enabled(boolean enabled) {
        put("enabled", enabled);
    }

    public boolean enabled() {
        return getAsBoolean("enabled");
    }

    public void serverWatchdog(boolean enabled) {
        put("server-watchdog", enabled);
    }

    public boolean serverWatchdog() {
        return getAsBoolean("server-watchdog");
    }
}
