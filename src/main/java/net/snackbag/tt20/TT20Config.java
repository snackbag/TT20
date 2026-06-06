package net.snackbag.tt20;

import dev.lemonnik.fern_config.CConfig;
import dev.lemonnik.fern_config.CExporter;
import dev.lemonnik.fern_config.types.CBoolean;
import dev.lemonnik.fern_config.types.CEnum;
import dev.lemonnik.fern_config.types.CMask;
import dev.lemonnik.fern_config.utils.CCategory;
import dev.lemonnik.fern_config.utils.MaskType;
import org.jetbrains.annotations.NotNull;

public class TT20Config extends CConfig {
    @Override
    public @NotNull String getFileName() {
        return TT20.MOD_ID;
    }

    @Override
    protected @NotNull CExporter.Format getFormat() {
        return CExporter.Format.JSON5;
    }

    private static final CCategory MAIN = CCategory.of("main", "Main TT20 configuration");

    public final CBoolean enabled             = register(MAIN, new CBoolean("enabled",           "Controls if TT20 is accelerating anything",                                                        true));
    public final CBoolean blockEntity         = register(MAIN, new CBoolean("blockEntity",       "ex. furnace work speed, this requires Block Entity Mask to be set up! (causes more lag otherwise)",false));
    public final CBoolean blockBreaking       = register(MAIN, new CBoolean("blockBreaking",     "block breaking acceleration",                                                                      true));
    public final CBoolean eating              = register(MAIN, new CBoolean("eating",            "food consumption acceleration",                                                                    true));
    public final CBoolean potionEffect        = register(MAIN, new CBoolean("potionEffect",      "effect duration acceleration",                                                                     true));
    public final CBoolean fluid               = register(MAIN, new CBoolean("fluid",             "fluid flow acceleration",                                                                          true));
    public final CBoolean pickup              = register(MAIN, new CBoolean("pickup",            "item pickup acceleration",                                                                         true));
    public final CBoolean portal              = register(MAIN, new CBoolean("portal",            "time in portal acceleration",                                                                      true));
    public final CBoolean sleeping            = register(MAIN, new CBoolean("sleeping",          "time laying in bed acceleration",                                                                  true));
    public final CBoolean time                = register(MAIN, new CBoolean("time",              "daytime acceleration",                                                                             true));
    public final CBoolean bow                 = register(MAIN, new CBoolean("bow",               "bow charge acceleration",                                                                          true));
    public final CBoolean crossbow            = register(MAIN, new CBoolean("crossbow",          "crossbow charge acceleration",                                                                     true));
    public final CBoolean randomTickSpeed     = register(MAIN, new CBoolean("randomTickSpeed",   "random tick speed acceleration (ex. crop growth)",                                                 true));
    public final CBoolean tnt                 = register(MAIN, new CBoolean("tnt",               "tnt timer acceleration",                                                                           false));
    public final CBoolean lagback             = register(MAIN, new CBoolean("lagback",           "vanilla player lagback",                                                                           true));
    public final CBoolean watchdog            = register(MAIN, new CBoolean("watchdog",          "server watchdog (vanilla's stop server after 1 minute of freeze behaviour)",                       true));

    public final CBoolean singlePlayerWarning = register(MAIN, new CBoolean("singlePlayerWarning","should show warn if loading in a singleplayer?",                                                  true));
    public final CBoolean updateChecker       = register(MAIN, new CBoolean("updateChecker",      "automatic update checker",                                                                        true));

    private static final CCategory BLOCK_ENTITY_MASK = CCategory.of("block_entity_mask", "Block Entity Mask configuration", "Only change this if you know what you're doing!");

    public final CEnum<MaskType> blockEntityMaskType = register(BLOCK_ENTITY_MASK, new CEnum<>("type", "mask type", MaskType.class, MaskType.WHITELIST));
    public final CMask blockEntityMask               = register(BLOCK_ENTITY_MASK, new CMask("blocks", "block entity mask", "type", this, "*:*"));
}
