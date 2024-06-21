package com.blueweabo.mutecore.api.gui;

import java.io.Serializable;

import com.cleanroommc.modularui.factory.PosGuiData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

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
