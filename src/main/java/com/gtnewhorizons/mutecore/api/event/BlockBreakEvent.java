package com.gtnewhorizons.mutecore.api.event;

import javax.annotation.Nonnull;

import com.badlogic.ashley.core.Entity;


public class BlockBreakEvent implements Comparable<BlockBreakEvent> {

    private int priority;
    private BlockBreak interaction;

    public BlockBreakEvent(int priority, @Nonnull BlockBreak interaction) {
        this.priority = priority;
        this.interaction = interaction;
    }

    public final @Nonnull void call(Entity entity) {
        interaction.executeEvent(entity);
    }

    @FunctionalInterface
    public static interface BlockBreak {

        @Nonnull
        void executeEvent(@Nonnull Entity entity);
    }

    @Override
    public int compareTo(@Nonnull BlockBreakEvent other) {
        return Integer.compare(priority, other.priority);
    }
}
