package com.gtnewhorizons.mutecore.api.gui;

import com.badlogic.ashley.core.Entity;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

public interface MuTEGUI {

    ModularPanel createGUI(Entity entity, PanelSyncManager syncManager);
}
