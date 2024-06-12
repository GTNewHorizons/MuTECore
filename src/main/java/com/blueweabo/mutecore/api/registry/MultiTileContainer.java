package com.blueweabo.mutecore.api.registry;

import java.lang.ref.WeakReference;

import com.blueweabo.mutecore.MuTECore;
import com.blueweabo.mutecore.api.data.BaseTexture;
import com.blueweabo.mutecore.api.tile.MultiTileEntity;

import dev.dominion.ecs.api.Entity;
import net.minecraft.util.ResourceLocation;

public class MultiTileContainer {

    private final Class<? extends MultiTileEntity> clazz;
    private final long id;
    private final WeakReference<MultiTileEntityRegistry> reg;
    private final Entity originalEntity;
    private IconContainer baseTexture;
    private IconContainer[][] overlayTextures = new IconContainer[6][];

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

    /**
     * Sets the texture path for the entity to use to get its textures from.
     * Uses mutecore as the modid.
     * They are automatically registered.
     */
    public MultiTileContainer texturePath(String path) {
        return texturePath(MuTECore.MODID, path);
    }

    /**
     * Sets the texture path for the entity to use to get its textures from.
     * Uses the modid given from the function to register the icons
     * They are automatically registered.
     */
    public MultiTileContainer texturePath(String modid, String path) {
        baseTexture = new IconContainer(new ResourceLocation(modid, path+"/base").toString());
        return this;
    }

    public boolean register() {
        if (baseTexture != null) TextureRegistry.addBlockIconToRegister(baseTexture);
        return reg.get() != null && reg.get()
            .register(id, this);
    }

    public Entity createNewEntity() {
        Entity newEntity = MuTECore.ENGINE.createEntityAs(originalEntity);
        newEntity.removeType(FakeEntity.class);
        if (baseTexture != null) newEntity.add(new BaseTexture(baseTexture.getIcon()));
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
