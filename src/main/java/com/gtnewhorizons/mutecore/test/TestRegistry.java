package com.gtnewhorizons.mutecore.test;

import net.minecraft.block.material.Material;

import com.gtnewhorizons.mutecore.api.block.MultiTileEntityBlock;
import com.gtnewhorizons.mutecore.api.item.MultiTileEntityItem;
import com.gtnewhorizons.mutecore.api.registry.MultiTileEntityRegistry;
import com.gtnewhorizons.mutecore.api.tile.MultiTileEntity;
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
            .gui((entity, syncManager) -> { return new ModularPanel("testOne"); })
            .unlocalizedName("testblockone")
            .register();
        REGISTRY.create(1, MultiTileEntity.class)
            .gui((entity, syncManager) -> { return new ModularPanel("testTwo"); })
            .unlocalizedName("testblocktwo")
            .register();
    }
}
