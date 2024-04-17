package com.blueweabo.mutecore.api.logic;

import java.util.UUID;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.blueweabo.mutecore.api.logic.interfaces.InventoryLogic;
import com.cleanroommc.modularui.api.IFluidTankLong;
import com.cleanroommc.modularui.utils.fluid.FluidTanksHandler;

import it.unimi.dsi.fastutil.objects.Object2LongMap;

@ParametersAreNonnullByDefault
public class FluidInventoryLogic implements InventoryLogic<IFluidTankLong> {

    private String name = "";
    private UUID id = UUID.randomUUID();
    private FluidTanksHandler handler;
    private Object2LongMap<IFluidTankLong> quickAccess;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public @Nullable IFluidTankLong extract(IFluidTankLong request, long amount, boolean doExtract) {
        int slot = (int) quickAccess.getLong(request);
        if (slot >= 0 && request.getRealFluid() == handler.getFluidInTank(slot)) {
            long drained = handler.getTank(slot).drainLong(amount, doExtract);
            IFluidTankLong drainedTank = request.copy();
            drainedTank.setFluid(drainedTank.getRealFluid(), drained);
            return drainedTank;
        }
        slot = findSlotFromFluidTank(request);
        if (slot < 0)
            return null;
        long drained = handler.getTank(slot).drainLong(amount, doExtract);
        IFluidTankLong drainedTank = request.copy();
        drainedTank.setFluid(drainedTank.getRealFluid(), drained);
        return drainedTank;
    }

    @Override
    public @Nullable IFluidTankLong insert(IFluidTankLong request, long amount, boolean doInsert) {
        int slot = (int) quickAccess.getLong(request);
        if (slot >= 0 && request.getRealFluid() == handler.getFluidInTank(slot)) {
            long filled = handler.getTank(slot).fillLong(request.getRealFluid(), amount, doInsert);
            IFluidTankLong filledTank = request.copy();
            filledTank.setFluid(filledTank.getRealFluid(), filled);
            return filledTank;
        }
        slot = findSlotFromFluidTank(request);
        if (slot < 0) {
            return null;
        }
        long filled = handler.getTank(slot).fillLong(request.getRealFluid(), amount, doInsert);
        IFluidTankLong filledTank = request.copy();
        filledTank.setFluid(filledTank.getRealFluid(), filled);
        return filledTank;
    }

    @Override
    public IFluidTankLong get(int slot) {
        return handler.getTank(slot);
    }

    @Override
    public int getSlots() {
        return handler.getTanks();
    }

    @Nullable
    protected int findSlotFromFluidTank(IFluidTankLong tank) {
        for (int i = 0; i < handler.getTanks(); i++) {
            if (tank.equals(handler.getTank(i))) {
                return i;
            }
        }
        return -1;
    }
}
