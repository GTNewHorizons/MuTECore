package com.blueweabo.mutecore.api.logic;

import javax.annotation.ParametersAreNonnullByDefault;

import com.blueweabo.mutecore.api.logic.interfaces.PowerLogic;
import com.cleanroommc.modularui.api.IFluidTankLong;
import com.cleanroommc.modularui.utils.fluid.FluidTankLong;

import net.minecraftforge.fluids.Fluid;

@ParametersAreNonnullByDefault
public class FluidPowerLogic implements PowerLogic {

    private FluidInventoryLogic container;
    private Fluid fluidToConsume;
    private long amountToConsume;

    public FluidPowerLogic(FluidInventoryLogic container, Fluid fluidToConsume, long amountToConsume) {
        this.container = container;
        this.fluidToConsume = fluidToConsume;
        this.amountToConsume = amountToConsume;
    }

    @Override
    public boolean consume(long tick) {
        IFluidTankLong toExtract = new FluidTankLong(fluidToConsume);
        IFluidTankLong extractedTry = container.extract(toExtract, amountToConsume, false);
        if (extractedTry == null || extractedTry.getFluidAmountLong() < amountToConsume) {
            return false;
        }
        container.extract(toExtract, amountToConsume, true);
        return true;
    }

    @Override
    public boolean generate(long tick) {
        return true;
    }
}
