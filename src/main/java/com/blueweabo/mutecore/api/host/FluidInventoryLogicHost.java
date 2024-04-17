package com.blueweabo.mutecore.api.host;

import com.blueweabo.mutecore.api.logic.FluidInventoryLogic;
import com.cleanroommc.modularui.api.IFluidTankLong;
import com.cleanroommc.modularui.utils.fluid.FluidTankLong;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public interface FluidInventoryLogicHost extends IFluidHandler {

    default FluidInventoryLogic getFluidLogic() {
        return getFluidLogic(ForgeDirection.UNKNOWN);
    }

    FluidInventoryLogic getFluidLogic(ForgeDirection side);

    @Override
    default boolean canDrain(ForgeDirection from, Fluid fluid) {
        FluidInventoryLogic logic = getFluidLogic(from);
        if (logic == null) return false;
        return logic.extract(new FluidTankLong(fluid), 1, false) != null;
    }

    @Override
    default boolean canFill(ForgeDirection from, Fluid fluid) {
        FluidInventoryLogic logic = getFluidLogic(from);
        if (logic == null) return false;
        return logic.insert(new FluidTankLong(fluid), 1, false) != null;
    }

    @Override
    default FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        FluidInventoryLogic logic = getFluidLogic(from);
        if (logic == null) return null;
        IFluidTankLong toDrain = new FluidTankLong(resource.getFluid());
        IFluidTankLong drainTry = logic.extract(toDrain, resource.amount, doDrain);
        return drainTry == null ? null : drainTry.getFluid();
    }

    @Override
    default FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidInventoryLogic logic = getFluidLogic(from);
        if (logic == null) return null;
        IFluidTankLong drainTry = logic.extract(null, maxDrain, doDrain);
        return drainTry == null ? null : drainTry.getFluid();
    }

    @Override
    default int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        FluidInventoryLogic logic = getFluidLogic(from);
        if (logic == null) return 0;
        IFluidTankLong toFill = new FluidTankLong(resource.getFluid());
        IFluidTankLong fillTry = logic.insert(toFill, resource.amount, doFill);
        return fillTry == null ? 0 : fillTry.getFluidAmount();
    }

    @Override
    default FluidTankInfo[] getTankInfo(ForgeDirection from) {
        FluidInventoryLogic logic = getFluidLogic(from);
        if (logic == null) return new FluidTankInfo[0];
        FluidTankInfo[] infos = new FluidTankInfo[logic.getSlots()];
        for (int i = 0; i < infos.length; i++) {

        }
        return infos;
    }
}
