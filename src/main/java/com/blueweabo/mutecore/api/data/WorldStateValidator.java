package com.blueweabo.mutecore.api.data;

import net.minecraft.nbt.NBTTagCompound;

public interface WorldStateValidator {

    void save(NBTTagCompound nbt);

    void load(NBTTagCompound nbt);
}
