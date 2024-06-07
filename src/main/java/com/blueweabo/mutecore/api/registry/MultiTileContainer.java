package com.blueweabo.mutecore.api.registry;

import java.lang.ref.WeakReference;

import com.blueweabo.mutecore.MuTECore;
import com.blueweabo.mutecore.api.tile.MultiTileEntity;

import dev.dominion.ecs.api.Entity;

public class MultiTileContainer {

    private final Class<? extends MultiTileEntity> clazz;
    private final long id;
    private final WeakReference<MultiTileEntityRegistry> reg;
    private final Entity originalEntity;

    public MultiTileContainer(MultiTileEntityRegistry reg, long id, Class<? extends MultiTileEntity> clazz) {
        this.reg = new WeakReference<>(reg);
        this.clazz = clazz;
        this.id = id;
        originalEntity = MuTECore.ENGINE.createEntity(new FakeEntity());
    }

    public MultiTileContainer addComponent(Object component) {
        originalEntity.add(component);
        return this;
    }

    public boolean register() {
        return reg.get() != null && reg.get()
            .register(id, this);
    }

    public Entity createNewEntity() {
        Entity newEntity = MuTECore.ENGINE.createEntityAs(originalEntity);
        newEntity.removeType(FakeEntity.class);
        return newEntity;
    }

    public MultiTileEntity createNewTileEntity() {
        try {
            MultiTileEntity entity = clazz.newInstance();
            return entity;
        } catch (Exception ex) {
            MuTECore.LOG.error("Unable to create a new TileEntity for class: " + clazz.getName());
            return null;
        }
    }

    public Class<? extends MultiTileEntity> getTileEntityClass() {
        return clazz;
    }

    private static class FakeEntity {
    }
}
