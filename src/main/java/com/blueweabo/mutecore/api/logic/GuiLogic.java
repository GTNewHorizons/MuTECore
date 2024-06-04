package com.blueweabo.mutecore.api.logic;

import javax.annotation.Nonnull;

import com.blueweabo.mutecore.api.host.GUIHost;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.GuiSyncManager;

public abstract class GuiLogic<M extends GUIHost<D>, D extends PosGuiData> {

    private final @Nonnull M machine;

    public GuiLogic(@Nonnull M machine) {
        this.machine = machine;
    }

    public abstract @Nonnull ModularPanel buildUI(D data, GuiSyncManager syncManager);

    protected @Nonnull M getMachine() {
        return machine;
    }
}
