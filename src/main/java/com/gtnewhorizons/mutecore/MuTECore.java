package com.gtnewhorizons.mutecore;

import net.minecraft.launchwrapper.Launch;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cleanroommc.modularui.factory.GuiManager;
import com.gtnewhorizons.mutecore.api.data.Coordinates;
import com.gtnewhorizons.mutecore.api.data.GUIEvent;
import com.gtnewhorizons.mutecore.api.data.WorldStateValidator;
import com.gtnewhorizons.mutecore.api.event.PlayerInteractionEvent;
import com.gtnewhorizons.mutecore.api.gui.MultiTileEntityGuiFactory;
import com.gtnewhorizons.mutecore.api.registry.EventRegistry;
import com.gtnewhorizons.mutecore.api.registry.MultiTileContainer.FakeEntity;
import com.gtnewhorizons.mutecore.api.tile.MultiTileEntity;
import com.gtnewhorizons.mutecore.api.utils.PlayerHelper;
import com.gtnewhorizons.mutecore.test.TestRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;
import dev.dominion.ecs.api.Results;
import dev.dominion.ecs.engine.IntEntity;
import micdoodle8.mods.galacticraft.core.event.EventHandlerGC;

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
    public static Dominion ENGINE;
    public static boolean ENABLE_TESTS;

    public MuTECore() {}

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ENGINE = Dominion.create("MuTE");
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
        SystemRegistrator.registerSystem(() -> {
            Results<Results.With1<GUIEvent>> results = MuTECore.ENGINE.findEntitiesWith(GUIEvent.class);
            for (Results.With1<GUIEvent> result : results) {
                Entity entity = result.entity();
                GUIEvent event = entity.get(GUIEvent.class);
                Coordinates coords = entity.get(Coordinates.class);
                NBTTagCompound nbt = new NBTTagCompound();
                Object[] components = ((IntEntity) entity).getComponentArray();
                for (Object component : components) {
                    if (component instanceof WorldStateValidator validator) {
                        validator.save(nbt);
                    }
                }
                MultiTileEntityGuiFactory.open(event.getPlayer(), coords.getX(), coords.getY(), coords.getZ(), nbt);
                entity.removeType(GUIEvent.class);
            }
        });
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

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        Results<Entity> results = ENGINE.findAllEntities();
        for (Entity entity : results) {
            if (entity.has(FakeEntity.class)) continue;
            ENGINE.deleteEntity(entity);
        }
    }
}
