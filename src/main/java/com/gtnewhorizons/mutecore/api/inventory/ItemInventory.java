package com.gtnewhorizons.mutecore.api.inventory;

import com.badlogic.ashley.core.Component;
import com.cleanroommc.modularui.utils.item.IItemStackLong;

public interface ItemInventory extends Component {

    IItemStackLong get(int index);

    void set(int index, IItemStackLong item);

    int getSize();

    long getLimit();
}
