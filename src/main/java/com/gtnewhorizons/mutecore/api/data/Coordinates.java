package com.gtnewhorizons.mutecore.api.data;

import net.minecraft.nbt.NBTTagCompound;

import com.badlogic.ashley.core.Component;

public class Coordinates implements Component, WorldStateValidator {

    private int x;
    private int y;
    private int z;

    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public void save(NBTTagCompound nbt) {
        NBTTagCompound coordinatesNBT = new NBTTagCompound();
        coordinatesNBT.setInteger("x", x);
        coordinatesNBT.setInteger("y", y);
        coordinatesNBT.setInteger("z", z);
        nbt.setTag("coordinates", coordinatesNBT);
    }

    @Override
    public void load(NBTTagCompound nbt) {
        NBTTagCompound coordinatesNBT = nbt.getCompoundTag("coordinates");
        x = coordinatesNBT.getInteger("x");
        y = coordinatesNBT.getInteger("y");
        z = coordinatesNBT.getInteger("z");
    }
}
