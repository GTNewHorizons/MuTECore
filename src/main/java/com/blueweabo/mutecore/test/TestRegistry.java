package com.blueweabo.mutecore.test;

import net.minecraft.block.material.Material;

import com.blueweabo.mutecore.api.block.MultiTileEntityBlock;
import com.blueweabo.mutecore.api.item.MultiTileEntityItem;
import com.blueweabo.mutecore.api.registry.MultiTileEntityRegistry;
import com.blueweabo.mutecore.api.tile.MultiTileEntity;
import com.cleanroommc.modularui.screen.ModularPanel;

import cpw.mods.fml.common.registry.GameRegistry;

public class TestRegistry implements Runnable {

    private static final MultiTileEntityRegistry REGISTRY;
    private static final MultiTileEntityBlock BLOCK;

    static {
        BLOCK = new MultiTileEntityBlock(Material.anvil);
        GameRegistry.registerBlock(BLOCK, MultiTileEntityItem.class, "mutecore.testtiles");
        REGISTRY = new MultiTileEntityRegistry(BLOCK);
        MultiTileEntityRegistry.registerRegistry(BLOCK, REGISTRY);
    }

    @Override
    public void run() {
        REGISTRY.create(0, MultiTileEntity.class)
            .texturePath("test")
            .gui((entity, syncManager) -> {
                return new ModularPanel("testOne");
            })
            .unlocalizedName("testblockone")
            .register();
        REGISTRY.create(1, MultiTileEntity.class)
            .texturePath("test")
            .gui((entity, syncManager) -> {
                return new ModularPanel("testTwo");
            })
            .unlocalizedName("testblocktwo")
            .register();
    }
}
