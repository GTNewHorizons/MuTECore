package com.blueweabo.mutecore.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.blueweabo.mutecore.api.block.MultiTileEntityBlock;
import com.blueweabo.mutecore.api.registry.MultiTileContainer;
import com.blueweabo.mutecore.api.registry.MultiTileEntityRegistry;
import com.blueweabo.mutecore.api.registry.MultiTileContainer.Id;
import com.blueweabo.mutecore.api.tile.MultiTileEntity;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import dev.dominion.ecs.api.Entity;

public class MultiTileBlockRenderer implements ISimpleBlockRenderingHandler {

    private final int renderId;
    public static MultiTileBlockRenderer INSTANCE;

    public MultiTileBlockRenderer() {
        renderId = RenderingRegistry.getNextAvailableRenderId();
        INSTANCE = this;
        RenderingRegistry.registerBlockHandler(this);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        if (!(block instanceof MultiTileEntityBlock mublock)) return false;

        TileEntity te = world.getTileEntity(x, y, z);
        if (!(te instanceof MultiTileEntity mute)) return false;

        Entity entity = mute.getEntity();
        MultiTileEntityRegistry reg = mublock.getRegistry();
        MultiTileContainer container = reg.getMultiTileContainer(entity.get(Id.class).getId());
        container.getRender().render(entity, renderer, x, y, z, world);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return renderId;
    }

}
