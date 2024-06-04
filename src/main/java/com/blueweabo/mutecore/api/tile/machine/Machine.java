package com.blueweabo.mutecore.api.tile.machine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.blueweabo.mutecore.api.host.FluidInventoryLogicHost;
import com.blueweabo.mutecore.api.host.GUIHost;
import com.blueweabo.mutecore.api.host.ItemInventoryLogicHost;
import com.blueweabo.mutecore.api.host.PowerLogicHost;
import com.blueweabo.mutecore.api.host.ProcessingLogicHost;
import com.blueweabo.mutecore.api.logic.FluidInventoryLogic;
import com.blueweabo.mutecore.api.logic.GuiLogic;
import com.blueweabo.mutecore.api.logic.ItemInventoryLogic;
import com.blueweabo.mutecore.api.logic.ProcessingLogic;
import com.blueweabo.mutecore.api.logic.interfaces.PowerLogic;
import com.blueweabo.mutecore.api.tile.base.MultiTileEntity;
import com.blueweabo.mutecore.api.tile.base.TickableMultiTileEntity;
import com.blueweabo.mutecore.client.MachineGuiFactory;
import com.cleanroommc.modularui.ModularUI;
import com.cleanroommc.modularui.api.UIFactory;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.factory.TileEntityGuiFactory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class Machine<P extends ProcessingLogic<P>, D extends PosGuiData> extends TickableMultiTileEntity implements IMachine, ItemInventoryLogicHost, FluidInventoryLogicHost, PowerLogicHost, GUIHost<D>, ProcessingLogicHost<P> {

    private @Nonnull PowerLogic power;
    private @Nonnull P processing;
    private @Nonnull ItemInventoryLogic item;
    private @Nonnull FluidInventoryLogic fluid;
    private @Nonnull GuiLogic<?, D> gui;

    public Machine() {
        super();
        this.item = createItem();
        this.fluid = createFluid();
        this.power = createPower();
        this.processing = createProcessing();
        this.gui = createGui();
    }

    protected abstract @Nonnull GuiLogic<?, D> createGui();

    @Override
    public @Nonnull GuiLogic<?, D> getGui() {
        return gui;
    }

    protected abstract @Nonnull PowerLogic createPower();

    @Override
    public @Nullable PowerLogic getPowerLogic(@Nonnull ForgeDirection side) {
        if (side == getFacing()) return null;
        return power;
    }

    protected abstract @Nonnull FluidInventoryLogic createFluid();

    @Override
    public @Nullable FluidInventoryLogic getFluidLogic(@Nonnull ForgeDirection side) {
        if (side == getFacing()) return null;
        return fluid;
    }

    protected abstract @Nonnull ItemInventoryLogic createItem();

    @Override
    public @Nullable ItemInventoryLogic getItemLogic(@Nonnull ForgeDirection side) {
        if (side == getFacing()) return null;
        return item;
    }

    protected abstract @Nonnull P createProcessing();

    @Override
    public @Nonnull P getProcessing() {
        return processing;
    }

    @Override
    public boolean onBlockActivated(EntityPlayer player, ForgeDirection side, float subX, float subY, float subZ) {
        if (super.onBlockActivated(player, side, subX, subY, subZ)) return true;
        MachineGuiFactory.open(player, this);
        return super.onBlockActivated(player, side, subX, subY, subZ);
    }

}
