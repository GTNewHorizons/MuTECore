package com.blueweabo.mutecore.api.host;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.ApiStatus.OverrideOnly;

import com.blueweabo.mutecore.api.logic.TickableTask;

public interface TaskHost {

    @OverrideOnly
    void registerTask(@Nonnull TickableTask<?> task);
}
