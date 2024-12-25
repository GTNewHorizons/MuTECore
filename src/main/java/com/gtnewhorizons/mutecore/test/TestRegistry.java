package com.gtnewhorizons.mutecore.test;

import net.minecraft.block.material.Material;

import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.widget.ScrollWidget;
import com.cleanroommc.modularui.widgets.ItemSlotLong;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.gtnewhorizons.mutecore.api.block.MultiTileEntityBlock;
import com.gtnewhorizons.mutecore.api.data.ItemInputInventory;
import com.gtnewhorizons.mutecore.api.inventory.ItemComponentInventoryHandler;
import com.gtnewhorizons.mutecore.api.inventory.ItemInventory;
import com.gtnewhorizons.mutecore.api.item.MultiTileEntityItem;
import com.gtnewhorizons.mutecore.api.registry.ComponentsCreator;
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
            .gui((entity, syncManager) -> {
                ItemInventory inventory = entity.getComponent(ItemInputInventory.class);
                ItemComponentInventoryHandler inventoryHandler = new ItemComponentInventoryHandler(inventory);
                final ScrollWidget<?> scrollable = new ScrollWidget<>();
                SlotGroupWidget slotGroup = new SlotGroupWidget();
                scrollable.size(64, 64)
                    .child(slotGroup);
                for (int row = 0; row * 4 < inventory.getSize() - 1; row++) {
                    int columnsToMake = Math.min(inventory.getSize() - row * 4, 4);
                    for (int column = 0; column < columnsToMake; column++) {
                        slotGroup.child(
                            new ItemSlotLong().slot(inventoryHandler, row * 4 + column)
                                .pos(column * 18, row * 18)
                                .size(18, 18));
                    }
                }

                return new ModularPanel("testOne").align(Alignment.Center)
                    .child(scrollable.align(Alignment.Center))
                    .bindPlayerInventory();
            })
            .componentsCreator(
                new ComponentsCreator().component(() -> new ItemInputInventory(3, 64))
                    .build())
            .unlocalizedName("testblockone")
            .register();
        TEST_REGISTRY.create(1, MultiTileEntity.class)
            .gui((entity, syncManager) ->

            { return new ModularPanel("testTwo").align(Alignment.BottomCenter); })
            .componentsCreator(new ComponentsCreator().build())
            .unlocalizedName("testblocktwo")
            .register();
    }

    public static void registerRenders() {
        TEST_REGISTRY.registerRender(0, (e, rb, x, y, z, w) -> {});
        TEST_REGISTRY.registerRender(1, (e, rb, x, y, z, w) -> {});
    }
}
