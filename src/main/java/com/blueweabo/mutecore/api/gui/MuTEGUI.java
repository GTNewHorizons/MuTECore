package com.blueweabo.mutecore.api.gui;

import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.GuiSyncManager;

import dev.dominion.ecs.api.Entity;

public interface MuTEGUI {
    ModularPanel createGUI(Entity entity, GuiSyncManager syncManager);
}
