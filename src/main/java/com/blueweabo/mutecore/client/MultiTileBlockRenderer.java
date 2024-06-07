package com.blueweabo.mutecore.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import com.blueweabo.mutecore.api.block.MultiTileEntityBlock;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class MultiTileBlockRenderer implements ISimpleBlockRenderingHandler {

    // public static final MultiTileRenderer INSTANCE = new MultiTileRenderer();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        if (!(block instanceof MultiTileEntityBlock muBlock)) return false;

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return 1024;
    }

}
