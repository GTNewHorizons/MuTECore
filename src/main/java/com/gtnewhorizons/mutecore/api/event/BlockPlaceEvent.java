package com.gtnewhorizons.mutecore.api.event;

import javax.annotation.Nonnull;

import dev.dominion.ecs.api.Entity;

public class BlockPlaceEvent implements Comparable<BlockPlaceEvent>{

    private int priority;
    private BlockPlace interaction;

    public BlockPlaceEvent(int priority, @Nonnull BlockPlace interaction) {
        this.priority = priority;
        this.interaction = interaction;
    }

    public final @Nonnull void call(Entity entity) {
        interaction.executeEvent(entity);
    }

    @FunctionalInterface
    public static interface BlockPlace {

        @Nonnull
        void executeEvent(@Nonnull Entity entity);
    }

    @Override
    public int compareTo(@Nonnull BlockPlaceEvent other) {
        return Integer.compare(priority, other.priority);
    }
}
