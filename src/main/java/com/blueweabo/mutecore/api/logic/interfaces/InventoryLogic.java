package com.blueweabo.mutecore.api.logic.interfaces;

import java.util.UUID;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface InventoryLogic<T> {

    String getName();

    UUID getId();

    @Nullable T extract(T request, long amount, boolean doExtract);

    @Nullable T insert(T request, long amount, boolean doInsert);
}
