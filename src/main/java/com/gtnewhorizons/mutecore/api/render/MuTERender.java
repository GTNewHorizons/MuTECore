package com.gtnewhorizons.mutecore.api.render;

import dev.dominion.ecs.api.Entity;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public interface MuTERender {
    void render(Entity entity, RenderBlocks render, int x, int y, int z, IBlockAccess world);
}
