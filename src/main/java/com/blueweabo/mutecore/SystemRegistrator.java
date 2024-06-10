package com.blueweabo.mutecore;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dev.dominion.ecs.api.Scheduler;

public class SystemRegistrator {

    private static final Scheduler SYSTEMS = MuTECore.ENGINE.createScheduler();

    /**
     * Registers a processing system, which run in parallel with all other processing systems.
     */
    public static void registerProcessingSystem() {
        SYSTEMS.schedule(() -> {});
    }

    /**
     * Registers a GUI system, which will run in parallel with all other GUI systems
     */
    public static void registerGUISystem() {

    }

    /**
     * Registers a system, which will be running after processing systems have ran.
     * Useful if one needs to generate their own system
     */
    public static void registerOtherSystem() {

    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        SYSTEMS.tick();
    }
}
