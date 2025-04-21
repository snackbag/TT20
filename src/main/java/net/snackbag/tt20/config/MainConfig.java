package net.snackbag.tt20.config;

import net.snackbag.shit.config.JSONConfiguration;

public class MainConfig extends JSONConfiguration {
    private boolean enabled = true;
    private boolean blockEntityAcceleration = false;
    private boolean blockBreakingAcceleration = true;
    private boolean eatingAcceleration = true;
    private boolean potionEffectAcceleration = true;
    private boolean fluidAcceleration = true;
    private boolean pickupAcceleration = true;
    private boolean portalAcceleration = true;
    private boolean sleepingAcceleration = true;
    private boolean timeAcceleration = true;
    private boolean randomTickSpeedAcceleration = true;
    private boolean serverWatchdog = true;

    private boolean singlePlayerWarning = true;
    private boolean automaticUpdater = true;

    public MainConfig() {
        super("config.json");

        putIfEmpty("enabled", enabled);
        putIfEmpty("block-entity-acceleration", blockEntityAcceleration);
        putIfEmpty("block-breaking-acceleration", blockBreakingAcceleration);
        putIfEmpty("potion-effect-acceleration", potionEffectAcceleration);
        putIfEmpty("fluid-acceleration", fluidAcceleration);
        putIfEmpty("pickup-acceleration", pickupAcceleration);
        putIfEmpty("eating-acceleration", eatingAcceleration);
        putIfEmpty("portal-acceleration", portalAcceleration);
        putIfEmpty("sleeping-acceleration", sleepingAcceleration);
        putIfEmpty("server-watchdog", serverWatchdog);
        putIfEmpty("singleplayer-warning", singlePlayerWarning);
        putIfEmpty("time-acceleration", timeAcceleration);
        putIfEmpty("random-tickspeed-acceleration", randomTickSpeedAcceleration);
        putIfEmpty("automatic-updater", automaticUpdater);

        save();
    }

    @Override
    public void reload() {
        super.reload();

        this.enabled = getAsBooleanOrDefault("enabled", enabled);
        this.blockEntityAcceleration = getAsBooleanOrDefault("block-entity-acceleration", blockEntityAcceleration);
        this.blockBreakingAcceleration = getAsBooleanOrDefault("block-breaking-acceleration", blockBreakingAcceleration);
        this.potionEffectAcceleration = getAsBooleanOrDefault("potion-effect-acceleration", potionEffectAcceleration);
        this.fluidAcceleration = getAsBooleanOrDefault("fluid-acceleration", fluidAcceleration);
        this.pickupAcceleration = getAsBooleanOrDefault("pickup-acceleration", pickupAcceleration);
        this.eatingAcceleration = getAsBooleanOrDefault("eating-acceleration", eatingAcceleration);
        this.portalAcceleration = getAsBooleanOrDefault("portal-acceleration", portalAcceleration);
        this.sleepingAcceleration = getAsBooleanOrDefault("sleeping-acceleration", sleepingAcceleration);
        this.serverWatchdog = getAsBooleanOrDefault("block-entity-acceleration", serverWatchdog);
        this.singlePlayerWarning = getAsBooleanOrDefault("singleplayer-warning", singlePlayerWarning);
        this.timeAcceleration = getAsBooleanOrDefault("time-acceleration", timeAcceleration);
        this.randomTickSpeedAcceleration = getAsBooleanOrDefault("random-tickspeed-acceleration", randomTickSpeedAcceleration);
        this.automaticUpdater = getAsBooleanOrDefault("automatic-updater", automaticUpdater);
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

    public void blockBreakingAcceleration(boolean enabled) {
        put("block-breaking-acceleration", enabled);
    }

    public boolean blockBreakingAcceleration() {
        return blockBreakingAcceleration;
    }

    public void potionEffectAcceleration(boolean enabled) {
        put("potion-effect-acceleration", enabled);
    }

    public boolean potionEffectAcceleration() {
        return potionEffectAcceleration;
    }

    public void fluidAcceleration(boolean enabled) {
        put("fluid-acceleration", enabled);
    }

    public boolean fluidAcceleration() {
        return fluidAcceleration;
    }

    public void pickupAcceleration(boolean enabled) {
        put("pickup-acceleration", enabled);
    }

    public boolean pickupAcceleration() {
        return pickupAcceleration;
    }

    public void eatingAcceleration(boolean enabled) {
        put("eating-acceleration", enabled);
    }

    public boolean eatingAcceleration() {
        return eatingAcceleration;
    }

    public void portalAcceleration(boolean enabled) {
        put("portal-acceleration", enabled);
    }

    public boolean portalAcceleration() {
        return portalAcceleration;
    }

    public void sleepingAcceleration(boolean enabled) {
        put("sleeping-acceleration", enabled);
    }

    public boolean sleepingAcceleration() {
        return sleepingAcceleration;
    }

    public void automaticUpdater(boolean enabled) {
        put("automatic-updater", enabled);
    }

    public boolean automaticUpdater() {
        return automaticUpdater;
    }

    public void singlePlayerWarning(boolean enabled) {
        put("singleplayer-warning", enabled);
    }

    public boolean timeAcceleration() {
        return timeAcceleration;
    }

    public void timeAcceleration(boolean enabled) {
        put("time-acceleration", enabled);
    }

    public boolean randomTickSpeedAcceleration() {
        return randomTickSpeedAcceleration;
    }

    public void randomTickSpeedAcceleration(boolean enabled) {
        put("random-tickspeed-acceleration", enabled);
    }

    public boolean singlePlayerWarning() {
        return singlePlayerWarning;
    }
}