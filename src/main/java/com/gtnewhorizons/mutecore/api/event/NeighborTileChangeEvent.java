package com.gtnewhorizons.mutecore.api.event;

import javax.annotation.Nonnull;

import dev.dominion.ecs.api.Entity;
import net.minecraft.tileentity.TileEntity;

public class NeighborTileChangeEvent implements Comparable<NeighborTileChangeEvent>{

    private int priority;
    private NeighborTileChange interaction;

    public NeighborTileChangeEvent(int priority, @Nonnull NeighborTileChange interaction) {
        this.priority = priority;
        this.interaction = interaction;
    }

    public final @Nonnull void call(@Nonnull TileEntity neighbor, @Nonnull Entity entity) {
        interaction.executeEvent(neighbor, entity);
    }

    @FunctionalInterface
    public static interface NeighborTileChange {

        @Nonnull
        void executeEvent(@Nonnull TileEntity neighbor, @Nonnull Entity entity);
    }

    @Override
    public int compareTo(@Nonnull NeighborTileChangeEvent other) {
        return Integer.compare(priority, other.priority);
    }
}
