package com.blueweabo.mutecore.api.host;

import javax.annotation.Nonnull;

import com.blueweabo.mutecore.api.logic.GuiLogic;
import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.GuiSyncManager;

import net.minecraft.network.PacketBuffer;

public interface GUIHost<D extends PosGuiData> extends IGuiHolder<D> {

    @Nonnull GuiLogic<?, D> getGui();

    @Nonnull D getGuiData();

    @Nonnull D readGuiData(@Nonnull PacketBuffer buffer);

    void writeGuiData(@Nonnull PosGuiData guiData, @Nonnull PacketBuffer buffer);

    void applyGuiData(@Nonnull D guiData);

    @Override
    default @Nonnull ModularPanel buildUI(D data, GuiSyncManager syncManager) {
        return getGui().buildUI(data, syncManager);
    }

}
