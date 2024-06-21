package com.blueweabo.mutecore.api.data;

import net.minecraft.world.IBlockAccess;

public class WorldContainer {

    private IBlockAccess world;

    public WorldContainer(IBlockAccess world) {
        this.world = world;
    }

    public IBlockAccess getWorld() {
        return world;
    }

    public void setWorld(IBlockAccess world) {
        this.world = world;
    }
}
