package com.gtnewhorizons.mutecore.api.event;

import javax.annotation.Nonnull;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import net.minecraft.entity.player.EntityPlayer;


public class PlayerInteractionEvent implements Comparable<PlayerInteractionEvent> {

    private int priority;
    private PlayerInteraction interaction;

    public PlayerInteractionEvent(int priority, @Nonnull PlayerInteraction interaction) {
        this.priority = priority;
        this.interaction = interaction;
    }

    public final @Nonnull Component generate(@Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return interaction.generateComponent(player, entity);
    }

    @FunctionalInterface
    public static interface PlayerInteraction {

        @Nonnull
        Component generateComponent(@Nonnull EntityPlayer player, @Nonnull Entity entity);
    }

    @Override
    public int compareTo(@Nonnull PlayerInteractionEvent other) {
        return Integer.compare(priority, other.priority);
    }
}
