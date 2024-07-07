package net.snackbag.tt20.config;

public class MainConfig extends JSONConfiguration {
    private boolean enabled = true;
    private boolean blockEntityAcceleration = false;
    private boolean potionEffectAcceleration = true;
    private boolean fluidAcceleration = true;
    private boolean serverWatchdog = true;

    public MainConfig() {
        super("config.json");

        putIfEmpty("enabled", enabled);
        putIfEmpty("block-entity-acceleration", blockEntityAcceleration);
        putIfEmpty("potion-effect-acceleration", potionEffectAcceleration);
        putIfEmpty("fluid-acceleration", fluidAcceleration);
        putIfEmpty("server-watchdog", serverWatchdog);

        save();
    }

    @Override
    public void reload() {
        super.reload();

        this.enabled = getAsBooleanOrDefault("enabled", enabled);
        this.blockEntityAcceleration = getAsBooleanOrDefault("block-entity-acceleration", blockEntityAcceleration);
        this.serverWatchdog = getAsBooleanOrDefault("block-entity-acceleration", serverWatchdog);
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

    public void potionEffectAcceleration(boolean enabled) {
        potionEffectAcceleration = enabled;
    }

    public boolean potionEffectAcceleration() {
        return potionEffectAcceleration;
    }

    public void fluidAcceleration(boolean enabled) {
        fluidAcceleration = enabled;
    }

    public boolean fluidAcceleration() {
        return fluidAcceleration;
    }
}
