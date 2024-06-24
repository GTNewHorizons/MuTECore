package com.blueweabo.mutecore;

import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

import com.blueweabo.mutecore.api.data.Coordinates;
import com.blueweabo.mutecore.api.data.GUIEvent;
import com.blueweabo.mutecore.api.data.WorldStateValidator;
import com.blueweabo.mutecore.api.gui.MultiTileEntityGuiFactory;
import com.blueweabo.mutecore.api.registry.EventRegistry;
import com.blueweabo.mutecore.api.registry.MultiTileContainer.Id;
import com.blueweabo.mutecore.api.registry.PlayerInteractionEvent;
import com.blueweabo.mutecore.api.utils.PlayerHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dev.dominion.ecs.api.Entity;
import dev.dominion.ecs.api.Results;
import dev.dominion.ecs.api.Scheduler;
import dev.dominion.ecs.engine.IntEntity;

public class SystemRegistrator {

    private static final Scheduler SYSTEMS = MuTECore.ENGINE.createScheduler();

    /**
     * Register a single system. It will be ran after the last registered system
     */
    public static void registerSystem(Runnable system) {
        SYSTEMS.schedule(system);
    }

    /**
     * Register any amount of systems. The systems will be scheduled one after another.
     */
    public static void registerSystems(Runnable... systems) {
        for (Runnable system : systems) {
            SYSTEMS.schedule(system);
        }
    }

    /**
     *  Register any amount of systems. The systems registered this way will be ran in parallel
     */
    public static void registerSystemsParallel(Runnable... systems) {
        SYSTEMS.parallelSchedule(systems);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        SYSTEMS.tick();
    }
}
