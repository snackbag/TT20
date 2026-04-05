package com.topi_banana.unitemplate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//? if FORGE
/* import net.minecraftforge.fml.common.Mod; */

//? if NEOFORGE
/* import net.neoforged.fml.common.Mod; */


//? if FORGE || NEOFORGE
/* @Mod("unitemplate") */
public class UniTemplateMod
        //? if FABRIC
        implements net.fabricmc.api.ModInitializer

        //? if PAPER
        /* extends org.bukkit.plugin.java.JavaPlugin */
{
    public static final Logger LOGGER = LogManager.getLogger();

    //? if FABRIC
    @Override public void onInitialize()
    //? if NEOFORGE
    /* public UniTemplateMod(net.neoforged.bus.api.IEventBus modBus) */
    //? if FORGE
    /* public UniTemplateMod() */
    //? if PAPER
    /* @Override public void onEnable() */
    {
        LOGGER.info("Hello, World!");
    }
}
