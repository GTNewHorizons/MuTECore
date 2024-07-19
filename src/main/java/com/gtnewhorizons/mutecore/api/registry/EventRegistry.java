package com.gtnewhorizons.mutecore.api.registry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.gtnewhorizons.mutecore.api.event.BlockBreakEvent;
import com.gtnewhorizons.mutecore.api.event.BlockPlaceEvent;
import com.gtnewhorizons.mutecore.api.event.NeighborBlockChangeEvent;
import com.gtnewhorizons.mutecore.api.event.NeighborTileChangeEvent;
import com.gtnewhorizons.mutecore.api.event.PlayerInteractionEvent;

public class EventRegistry {

    public static final @Internal @Nonnull List<PlayerInteractionEvent> PLAYER_INTERACTION_EVENTS = new ArrayList<>();
    public static final @Internal @Nonnull List<NeighborBlockChangeEvent> NEIGHBOR_BLOCK_CHANGE_EVENTS = new ArrayList<>();
    public static final @Internal @Nonnull List<NeighborTileChangeEvent> NEIGHBOR_TILE_CHANGE_EVENTS = new ArrayList<>();
    public static final @Internal @Nonnull List<BlockBreakEvent> BLOCK_BREAK_EVENTS = new ArrayList<>();
    public static final @Internal @Nonnull List<BlockPlaceEvent> BLOCK_PLACE_EVENTS = new ArrayList<>();

    /**
     * Register a player interaction event processor. This is not the processing of the event,
     * but the way to delegate it to said processing.
     */
    public static void registerPlayerInteractionEvent(@Nonnull PlayerInteractionEvent event) {
        PLAYER_INTERACTION_EVENTS.add(event);
        PLAYER_INTERACTION_EVENTS.sort(Comparator.reverseOrder());
    }

    /**
     * Register a neighbor block change event processor. This does the actual processing for the event.
     */
    public static void registerNeighborBlockChangeEvent(@Nonnull NeighborBlockChangeEvent event) {
        NEIGHBOR_BLOCK_CHANGE_EVENTS.add(event);
        NEIGHBOR_BLOCK_CHANGE_EVENTS.sort(Comparator.reverseOrder());
    }

    /**
     * Register a neighbor tile change event processor. This does the actual processing for the event.
     */
    public static void registerNeighborTileChangeEvent(@Nonnull NeighborTileChangeEvent event) {
        NEIGHBOR_TILE_CHANGE_EVENTS.add(event);
        NEIGHBOR_TILE_CHANGE_EVENTS.sort(Comparator.reverseOrder());
    }

    /**
     * Register a block break event processor. This does the actual processing for the event.
     */
    public static void registerBlockBreakEvent(@Nonnull BlockBreakEvent event) {
        BLOCK_BREAK_EVENTS.add(event);
        BLOCK_BREAK_EVENTS.sort(Comparator.reverseOrder());
    }

    /**
     * Register a block place event processor. This does the actual processing for the event.
     */
    public static void registerBlockPlaceEvent(@Nonnull BlockPlaceEvent event) {
        BLOCK_PLACE_EVENTS.add(event);
        BLOCK_PLACE_EVENTS.sort(Comparator.reverseOrder());
    }
}
