package com.gtnewhorizons.mutecore;

import com.badlogic.ashley.core.EntitySystem;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class SystemRegistrator {

    /**
     * Register a single system. It will be ran after the last registered system
     */
    public static void registerSystem(EntitySystem system) {
        MuTECore.ENGINE.addSystem(system);
    }

    /**
     * Register any amount of systems. The systems will be scheduled one after another.
     */
    public static void registerSystems(EntitySystem... systems) {
        for (EntitySystem system : systems) {
            MuTECore.ENGINE.addSystem(system);
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        MuTECore.ENGINE.update(1);
    }
}
