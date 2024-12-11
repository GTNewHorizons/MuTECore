package com.gtnewhorizons.mutecore.api.render;

import com.badlogic.ashley.core.Entity;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public interface MuTERender {

    void render(Entity entity, RenderBlocks render, int x, int y, int z, IBlockAccess world);
}
