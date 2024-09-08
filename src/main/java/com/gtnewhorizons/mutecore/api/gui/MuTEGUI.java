package com.gtnewhorizons.mutecore.api.gui;

import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import dev.dominion.ecs.api.Entity;

public interface MuTEGUI {

    ModularPanel createGUI(Entity entity, PanelSyncManager syncManager);
}
