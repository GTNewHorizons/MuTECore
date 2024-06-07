package com.blueweabo.mutecore;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class Runner {

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        MuTECore.SYSTEMS.tick();
    }
}
