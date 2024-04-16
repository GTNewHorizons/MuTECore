package com.blueweabo.mutecore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = MuTECore.MODID, version = Tags.VERSION, name = "MuTECore", acceptedMinecraftVersions = "[1.7.10]", dependencies = MuTECore.DEPENDENCIES)
public class MuTECore {

    public static final String MODID = "mutecore";
    public static final Logger LOG = LogManager.getLogger(MODID);
    public static final String DEPENDENCIES = "required-after:gtnhlib@[0.2.7,);" +
        "required-after:modularui;" +
        "after:appliedenegistics2";

    @SidedProxy(clientSide = "com.blueweabo.mutecore.ClientProxy", serverSide = "com.blueweabo.mutecore.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
