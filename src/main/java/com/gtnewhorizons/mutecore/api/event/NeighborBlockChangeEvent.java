package com.gtnewhorizons.mutecore.api.event;

import javax.annotation.Nonnull;

import dev.dominion.ecs.api.Entity;
import net.minecraft.block.Block;

public class NeighborBlockChangeEvent implements Comparable<NeighborBlockChangeEvent>{

    private int priority;
    private NeighborBlockChange interaction;

    public NeighborBlockChangeEvent(int priority, @Nonnull NeighborBlockChange interaction) {
        this.priority = priority;
        this.interaction = interaction;
    }

    public final @Nonnull void call(@Nonnull Block neighbor, int x, int y, int z, @Nonnull Entity entity) {
        interaction.executeEvent(neighbor, x, y, z, entity);
    }

    @FunctionalInterface
    public static interface NeighborBlockChange {

        @Nonnull
        void executeEvent(@Nonnull Block neighbor, int x, int y, int z, @Nonnull Entity entity);
    }

    @Override
    public int compareTo(@Nonnull NeighborBlockChangeEvent other) {
        return Integer.compare(priority, other.priority);
    }
}
