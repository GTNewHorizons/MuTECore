package com.blueweabo.mutecore.api.logic;

import javax.annotation.Nonnull;

import com.blueweabo.mutecore.api.host.TaskHost;

import net.minecraft.nbt.NBTTagCompound;

public abstract class TickableTask<T extends TaskHost> {

    private final @Nonnull T taskHost;

    public TickableTask(T taskHost) {
        this.taskHost = taskHost;
        taskHost.registerTask(this);
    }

    public void updateServer(long tick) {}

    public void updateClient(long tick) {}

    public abstract @Nonnull String getName();

    public void writeToNBT(@Nonnull NBTTagCompound nbt) {}

    public void readFromNBT(@Nonnull NBTTagCompound nbt) {}

    @Override
    public String toString() {
        return "Task{"+"name="+getName()+",taskHost="+taskHost+"}";
    }
}
