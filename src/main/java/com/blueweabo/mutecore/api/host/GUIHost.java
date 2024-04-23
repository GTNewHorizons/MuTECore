package com.blueweabo.mutecore.api.host;

import com.blueweabo.mutecore.api.logic.GuiLogic;
import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.GuiSyncManager;

public interface GUIHost<D extends PosGuiData> extends IGuiHolder<D> {

    GuiLogic<?, D> getGui();

    @Override
    default ModularPanel buildUI(D data, GuiSyncManager syncManager) {
        return getGui().buildUI(data, syncManager);
    }

}
