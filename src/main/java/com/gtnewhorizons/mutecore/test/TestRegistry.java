package com.gtnewhorizons.mutecore.test;

import net.minecraft.block.material.Material;

import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.utils.Alignment;
import com.gtnewhorizons.mutecore.api.block.MultiTileEntityBlock;
import com.gtnewhorizons.mutecore.api.item.MultiTileEntityItem;
import com.gtnewhorizons.mutecore.api.registry.MultiTileEntityRegistry;
import com.gtnewhorizons.mutecore.api.tile.MultiTileEntity;

import cpw.mods.fml.common.registry.GameRegistry;

public class TestRegistry implements Runnable {

    private static final MultiTileEntityRegistry TEST_REGISTRY;
    private static final MultiTileEntityBlock TEST_BLOCK;

    static {
        TEST_BLOCK = new MultiTileEntityBlock(Material.anvil);
        GameRegistry.registerBlock(TEST_BLOCK, MultiTileEntityItem.class, "mutecore.testtiles");
        TEST_REGISTRY = new MultiTileEntityRegistry(TEST_BLOCK);
        MultiTileEntityRegistry.registerRegistry(TEST_BLOCK, TEST_REGISTRY);
    }

    @Override
    public void run() {
        TEST_REGISTRY.create(0, MultiTileEntity.class)
            .gui((entity, syncManager) -> { return new ModularPanel("testOne").align(Alignment.Center); })
            .unlocalizedName("testblockone")
            .register();
        TEST_REGISTRY.create(1, MultiTileEntity.class)
            .gui((entity, syncManager) -> { return new ModularPanel("testTwo").align(Alignment.BottomCenter); })
            .unlocalizedName("testblocktwo")
            .register();
    }

    public static void registerRenders() {
        TEST_REGISTRY.registerRender(0, (e, rb, x, y, z, w) -> {});
        TEST_REGISTRY.registerRender(1, (e, rb, x, y, z, w) -> {});
    }
}
