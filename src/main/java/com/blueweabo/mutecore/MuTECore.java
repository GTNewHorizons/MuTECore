package com.blueweabo.mutecore;

import net.minecraft.launchwrapper.Launch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blueweabo.mutecore.api.tile.MultiTileEntity;
import com.blueweabo.mutecore.test.TestRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dev.dominion.ecs.api.Dominion;

@Mod(modid = MuTECore.MODID, version = Tags.VERSION, name = "MuTECore", acceptedMinecraftVersions = "[1.7.10]", dependencies = MuTECore.DEPENDENCIES)
public class MuTECore {

    public static final String MODID = "mutecore";
    public static final Logger LOG = LogManager.getLogger(MODID);
    public static final String DEPENDENCIES = "required-after:gtnhlib@[0.2.7,);" + "required-after:modularui;"
            + "after:appliedenegistics2";
    @SidedProxy(clientSide = "com.blueweabo.mutecore.ClientProxy", serverSide = "com.blueweabo.mutecore.CommonProxy")
    public static CommonProxy proxy;
    public static Dominion ENGINE;
    public static boolean ENABLE_TESTS;

    public MuTECore() {
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ENGINE = Dominion.create("MuTE");
        ENABLE_TESTS = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
        if (ENABLE_TESTS) {
            new TestRegistry().run();
            SystemRegistrator.registerProcessingSystem();
            SystemRegistrator.registerOtherSystem();
        }
        GameRegistry.registerTileEntity(MultiTileEntity.class, "multitileentity");
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        FMLCommonHandler.instance()
                .bus()
                .register(new SystemRegistrator());
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
