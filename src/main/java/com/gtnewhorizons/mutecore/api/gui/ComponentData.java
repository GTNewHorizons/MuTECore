package com.gtnewhorizons.mutecore.api.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.cleanroommc.modularui.factory.PosGuiData;

public class ComponentData extends PosGuiData {

    private NBTTagCompound componentData;

    public ComponentData(EntityPlayer player, int x, int y, int z, NBTTagCompound componentData) {
        super(player, x, y, z);
        this.componentData = componentData;
    }

    public NBTTagCompound getComponentData() {
        return componentData;
    }
}
