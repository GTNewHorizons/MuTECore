package com.blueweabo.mutecore.api.registry;

import net.minecraft.tileentity.TileEntity;

import com.blueweabo.mutecore.MuTECore;
import com.blueweabo.mutecore.api.block.MultiTileEntityBlock;
import com.blueweabo.mutecore.api.tile.MultiTileEntity;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

public class MultiTileEntityRegistry {

    private final MultiTileEntityBlock block;
    private final Long2ObjectMap<MultiTileContainer> map = new Long2ObjectOpenHashMap<>();

    public MultiTileEntityRegistry(MultiTileEntityBlock block) {
        this.block = block;
        block.setRegistry(this);
    }

    public MultiTileContainer create(long id, Class<? extends MultiTileEntity> clazz) {
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

    public TileEntity createNewTileEntity(int id) {
        MultiTileContainer container = map.get(id);
        MultiTileEntity tileEntity = container.createNewTileEntity();
        tileEntity.setEntity(container.createNewEntity());
        return tileEntity;
    }
}
