package com.gtnewhorizons.mutecore.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.badlogic.ashley.core.Entity;
import com.gtnewhorizons.mutecore.api.block.MultiTileEntityBlock;
import com.gtnewhorizons.mutecore.api.registry.MultiTileContainer.Id;
import com.gtnewhorizons.mutecore.api.registry.MultiTileEntityRegistry;
import com.gtnewhorizons.mutecore.api.tile.MultiTileEntity;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

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
        if (!(block instanceof MultiTileEntityBlock mublock)) return;

        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        MultiTileEntityRegistry reg = mublock.getRegistry();
        reg.getRender(metadata).render(reg.getMultiTileContainer(metadata).getFakeEntity(), renderer, 0, 0, 0, null);

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        if (!(block instanceof MultiTileEntityBlock mublock)) return false;

        TileEntity te = world.getTileEntity(x, y, z);
        if (!(te instanceof MultiTileEntity mute)) return false;

        Entity entity = mute.getEntity();
        MultiTileEntityRegistry reg = mublock.getRegistry();
        reg.getRender(
            entity.getComponent(Id.class)
                .getId())
            .render(entity, renderer, x, y, z, world);

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
