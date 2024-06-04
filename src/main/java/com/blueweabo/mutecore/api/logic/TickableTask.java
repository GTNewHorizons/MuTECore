package com.blueweabo.mutecore.api.logic;

import javax.annotation.Nonnull;

import com.blueweabo.mutecore.api.host.TaskHost;

import net.minecraft.nbt.NBTTagCompound;

public abstract class TickableTask<T extends TaskHost> implements Comparable<TickableTask<T>> {

    private final @Nonnull T taskHost;
    private int priority;

    public TickableTask(T taskHost, int priority) {
        this.taskHost = taskHost;
        this.priority = priority;
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

    @Override
    public int compareTo(TickableTask<T> other) {
        return priority - other.priority;
    }
}
