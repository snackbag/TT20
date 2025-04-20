package net.snackbag.tt20;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TT20.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLED;
    public static final ForgeConfigSpec.ConfigValue<Boolean> BLOCK_ENTITY_ACCELERATION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> BLOCK_BREAKING_ACCELERATION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> POTION_EFFECT_ACCELERATION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> FLUID_ACCELERATION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> PICKUP_ACCELERATION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> EATING_ACCELERATION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> PORTAL_ACCELERATION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SLEEPING_ACCELERATION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SERVER_WATCHDOG;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SINGLEPLAYER_WARNING;
    public static final ForgeConfigSpec.ConfigValue<Boolean> TIME_ACCELERATION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> RANDOM_TICKSPEED_ACCELERATION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> AUTOMATIC_UPDATER;

    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("TT20 Config");

        ENABLED                       = BUILDER.define("enabled", true);
        BLOCK_ENTITY_ACCELERATION     = BUILDER.define("block-entity-acceleration", false);
        BLOCK_BREAKING_ACCELERATION   = BUILDER.define("block-breaking-acceleration", true);
        POTION_EFFECT_ACCELERATION    = BUILDER.define("potion-effect-acceleration", true);
        FLUID_ACCELERATION            = BUILDER.define("fluid-acceleration", true);
        PICKUP_ACCELERATION           = BUILDER.define("pickup-acceleration", true);
        EATING_ACCELERATION           = BUILDER.define("eating-acceleration", true);
        PORTAL_ACCELERATION           = BUILDER.define("portal-acceleration", true);
        SLEEPING_ACCELERATION         = BUILDER.define("sleeping-acceleration", true);
        SERVER_WATCHDOG               = BUILDER.define("server-watchdog", true);
        SINGLEPLAYER_WARNING          = BUILDER.define("singleplayer-warning", true);
        TIME_ACCELERATION             = BUILDER.define("time-acceleration", true);
        RANDOM_TICKSPEED_ACCELERATION = BUILDER.define("random-tickspeed-acceleration", true);
        AUTOMATIC_UPDATER             = BUILDER.define("automatic-updater", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
