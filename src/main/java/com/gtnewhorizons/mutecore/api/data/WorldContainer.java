package com.gtnewhorizons.mutecore.api.data;

import net.minecraft.world.World;

import com.badlogic.ashley.core.Component;

public class WorldContainer implements Component {

    private World world;

    public WorldContainer(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
