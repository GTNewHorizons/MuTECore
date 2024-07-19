package com.gtnewhorizons.mutecore.api.registry;

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

    public final @Nonnull Object generate(@Nonnull TileEntity neighbor, @Nonnull Entity entity) {
        return interaction.generateComponent(neighbor, entity);
    }

    @FunctionalInterface
    public static interface NeighborTileChange {

        @Nonnull
        Object generateComponent(@Nonnull TileEntity neighbor, @Nonnull Entity entity);
    }

    @Override
    public int compareTo(@Nonnull NeighborTileChangeEvent other) {
        return Integer.compare(priority, other.priority);
    }
}
