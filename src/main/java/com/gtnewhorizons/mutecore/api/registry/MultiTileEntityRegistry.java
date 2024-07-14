package com.gtnewhorizons.mutecore.api.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.gtnewhorizons.mutecore.MuTECore;
import com.gtnewhorizons.mutecore.api.block.MultiTileEntityBlock;
import com.gtnewhorizons.mutecore.api.render.MuTERender;
import com.gtnewhorizons.mutecore.api.tile.MultiTileEntity;

import com.cleanroommc.modularui.utils.item.ItemStackLong;
import com.google.common.primitives.Ints;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class MultiTileEntityRegistry {

    private static final Int2ObjectMap<MultiTileEntityRegistry> REGISTRY_MAP = new Int2ObjectOpenHashMap<>();

    private final MultiTileEntityBlock block;
    private final Int2ObjectMap<MultiTileContainer> map = new Int2ObjectOpenHashMap<>();
    private final Int2ObjectMap<MuTERender> renderMap = new Int2ObjectOpenHashMap<>();

    /**
     * Should only be called after the block has been registered
     */
    public static void registerRegistry(MultiTileEntityBlock block, MultiTileEntityRegistry registry) {
        int id = Block.getIdFromBlock(block);
        REGISTRY_MAP.put(id, registry);
    }

    @Internal
    public static MultiTileEntityRegistry getRegistry(int id) {
        return REGISTRY_MAP.get(id);
    }

    public MultiTileEntityRegistry(MultiTileEntityBlock block) {
        this.block = block;
        block.setRegistry(this);
    }

    public MultiTileContainer create(int id, Class<? extends MultiTileEntity> clazz) {
        return new MultiTileContainer(this, id, clazz);
    }

    public void registerRender(int id, MuTERender render) {
        renderMap.put(id, render);
    }

    public MuTERender getRender(int id) {
        return renderMap.get(id);
    }

    @Internal
    public boolean register(int id, MultiTileContainer container) {
        if (map.containsKey(id)) {
            MuTECore.LOG.error(
                "There already exists a MultiTileEntity for id: " + id + " and block: " + block.getUnlocalizedName());
            return false;
        }
        map.put(id, container);
        return true;
    }

    public MultiTileContainer getMultiTileContainer(int id) {
        return map.get(id);
    }

    public TileEntity createNewTileEntity(int id) {
        MultiTileContainer container = map.get(id);
        MultiTileEntity tileEntity = container.createNewTileEntity();
        tileEntity.setEntity(container.createNewEntity());
        return tileEntity;
    }

    public boolean isIdUsed(int id) {
        return map.containsKey(id);
    }

    public List<ItemStack> getAllItemStacks() {
        List<ItemStack> list = new ArrayList<>();
        map.forEach((id, fake) -> { list.add(getItemStack(Ints.saturatedCast(id))); });
        return list;
    }

    public ItemStack getItemStack(int id) {
        return getItemStack(id, 1);
    }

    public ItemStack getItemStack(int id, int stackSize) {
        return getItemStackLong(id, stackSize).getAsItemStack();
    }

    public ItemStackLong getItemStackLong(int id, long stackSize) {
        return new ItemStackLong(Item.getItemFromBlock(block), 64, id, stackSize);
    }

    @Internal
    public int getBlockId() {
        return Block.getIdFromBlock(block);
    }
}
