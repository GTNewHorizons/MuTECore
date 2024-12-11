package com.gtnewhorizons.mutecore.api.data;

import com.badlogic.ashley.core.Component;

import net.minecraft.world.World;

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
