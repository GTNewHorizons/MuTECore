package com.blueweabo.mutecore.api.registry;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;

import dev.dominion.ecs.api.Entity;

public class PlayerInteractionEvent implements Comparable<PlayerInteractionEvent> {

    private int priority;
    private PlayerInteraction interaction;

    public PlayerInteractionEvent(int priority, @Nonnull PlayerInteraction interaction) {
        this.priority = priority;
        this.interaction = interaction;
    }

    public final @Nonnull Object generate(@Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return interaction.generateComponent(player, entity);
    }

    @FunctionalInterface
    public static interface PlayerInteraction {

        @Nonnull
        Object generateComponent(@Nonnull EntityPlayer player, @Nonnull Entity entity);
    }

    @Override
    public int compareTo(@Nonnull PlayerInteractionEvent other) {
        return Integer.compare(priority, other.priority);
    }
}
