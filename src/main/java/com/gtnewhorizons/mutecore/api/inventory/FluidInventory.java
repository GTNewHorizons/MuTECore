package com.gtnewhorizons.mutecore.api.inventory;

import com.badlogic.ashley.core.Component;
import com.cleanroommc.modularui.utils.fluid.IFluidTankLong;

public interface FluidInventory extends Component {

    IFluidTankLong get(int index);

    void set(int index, IFluidTankLong tank);

    int getSize();
}
