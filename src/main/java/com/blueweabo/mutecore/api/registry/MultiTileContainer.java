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

    public MultiTileContainer addComponents(Object... components) {
        for (Object component : components) {
            originalEntity.add(component);
        }
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MultiTileContainer other = (MultiTileContainer) obj;
        if (id != other.id)
            return false;
        return true;
    }


    private static class FakeEntity {
    }
}
