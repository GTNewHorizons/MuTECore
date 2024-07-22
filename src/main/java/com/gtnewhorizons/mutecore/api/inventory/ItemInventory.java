package com.gtnewhorizons.mutecore.api.inventory;

import com.cleanroommc.modularui.api.IItemStackLong;

public interface ItemInventory {

    IItemStackLong get(int index);

    void set(int index, IItemStackLong item);

    int getSize();

    long getLimit();
}
