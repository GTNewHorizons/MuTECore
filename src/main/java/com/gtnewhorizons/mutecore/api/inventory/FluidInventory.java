package com.gtnewhorizons.mutecore.api.inventory;

import com.cleanroommc.modularui.api.IFluidTankLong;

public interface FluidInventory {

    IFluidTankLong get(int index);

    void set(int index, IFluidTankLong tank);

    int getSize();
}
