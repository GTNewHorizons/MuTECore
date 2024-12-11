package com.gtnewhorizons.mutecore.api.data;

import com.badlogic.ashley.core.Component;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class Direction implements Component, WorldStateValidator {

    private ForgeDirection direction;

    public ForgeDirection getDirection() {
        return direction;
    }

    public void setDirection(ForgeDirection direction) {
        this.direction = direction;
    }

    public Direction(ForgeDirection direction) {
        this.direction = direction;
    }

    @Override
    public void save(NBTTagCompound nbt) {
        NBTTagCompound directionNBT = new NBTTagCompound();
        directionNBT.setInteger("i", direction.ordinal());
        nbt.setTag("direction", directionNBT);
    }

    @Override
    public void load(NBTTagCompound nbt) {
        direction = ForgeDirection.getOrientation(
            nbt.getCompoundTag("direction")
                .getInteger("i"));
    }
}
