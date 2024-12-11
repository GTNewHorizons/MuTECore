package com.gtnewhorizons.mutecore;

import net.minecraft.launchwrapper.Launch;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.cleanroommc.modularui.factory.GuiManager;
import com.gtnewhorizons.mutecore.api.data.Coordinates;
import com.gtnewhorizons.mutecore.api.data.GUIEvent;
import com.gtnewhorizons.mutecore.api.data.WorldStateValidator;
import com.gtnewhorizons.mutecore.api.event.PlayerInteractionEvent;
import com.gtnewhorizons.mutecore.api.gui.GUISystem;
import com.gtnewhorizons.mutecore.api.gui.MultiTileEntityGuiFactory;
import com.gtnewhorizons.mutecore.api.registry.EventRegistry;
import com.gtnewhorizons.mutecore.api.registry.MultiTileContainer.FakeEntity;
import com.gtnewhorizons.mutecore.api.registry.MultiTileEntityRegistry;
import com.gtnewhorizons.mutecore.api.tile.MultiTileEntity;
import com.gtnewhorizons.mutecore.api.utils.PlayerHelper;
import com.gtnewhorizons.mutecore.test.TestRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(
    modid = MuTECore.MODID,
    version = Tags.VERSION,
    name = "MuTECore",
    acceptedMinecraftVersions = "[1.7.10]",
    dependencies = MuTECore.DEPENDENCIES)
public class MuTECore {

    public static final String MODID = "mutecore";
    public static final Logger LOG = LogManager.getLogger(MODID);
    public static final String DEPENDENCIES = "required-after:gtnhlib@[0.2.7,);" + "required-after:modularui2;"
        + "after:appliedenegistics2";
    @SidedProxy(
        clientSide = "com.gtnewhorizons.mutecore.ClientProxy",
        serverSide = "com.gtnewhorizons.mutecore.CommonProxy")
    public static CommonProxy proxy;
    public static Engine ENGINE;
    public static boolean ENABLE_TESTS;

    public MuTECore() {}

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ENGINE = new Engine();
        ENABLE_TESTS = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
        if (ENABLE_TESTS) {
            new TestRegistry().run();
        }
        GameRegistry.registerTileEntity(MultiTileEntity.class, "multitileentity");
        GuiManager.registerFactory(MultiTileEntityGuiFactory.INSTANCE);
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent initEvent) {
        proxy.init(initEvent);
        EventRegistry.registerPlayerInteractionEvent(
            new PlayerInteractionEvent(0, (p, e) -> PlayerHelper.isRealPlayer(p) ? new GUIEvent(p) : null));
        SystemRegistrator.registerSystem(new GUISystem());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        FMLCommonHandler.instance()
            .bus()
            .register(new SystemRegistrator());
    }

    @Mod.EventHandler
    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        MultiTileEntityRegistry.registerForSave();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        ImmutableArray<Entity> results = ENGINE.getEntities();
        for (Entity entity : results) {
            ENGINE.removeEntity(entity);
        }
    }
}
