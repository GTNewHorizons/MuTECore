package com.blueweabo.mutecore.api.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

import com.blueweabo.mutecore.MuTECore;
import com.blueweabo.mutecore.api.block.MultiTileEntityBlock;
import com.blueweabo.mutecore.api.tile.MultiTileEntity;
import com.cleanroommc.modularui.utils.item.ItemStackLong;
import com.google.common.primitives.Ints;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

public class MultiTileEntityRegistry {

    private final MultiTileEntityBlock block;
    private final Long2ObjectMap<MultiTileContainer> map = new Long2ObjectOpenHashMap<>();

    public MultiTileEntityRegistry(MultiTileEntityBlock block) {
        this.block = block;
        block.setRegistry(this);
    }

    public MultiTileContainer create(int id, Class<? extends MultiTileEntity> clazz) {
        return new MultiTileContainer(this, id, clazz);
    }

    public boolean register(long id, MultiTileContainer container) {
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
        map.forEach((id, fake) -> {list.add(getItemStack(Ints.saturatedCast(id)));});
        return list;
    }

    public ItemStack getItemStack(int id) {
        return getItemStack(id, 0);
    }

    public ItemStack getItemStack(int id, int stackSize) {
        return getItemStackLong(id, stackSize).getAsItemStack();
    }

    public ItemStackLong getItemStackLong(int id, long stackSize) {
        return new ItemStackLong(Item.getItemFromBlock(block), 64, id, stackSize);
    }
}
