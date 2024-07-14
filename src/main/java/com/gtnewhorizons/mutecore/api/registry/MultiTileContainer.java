package com.gtnewhorizons.mutecore.api.registry;

import java.lang.ref.WeakReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.mutecore.MuTECore;
import com.gtnewhorizons.mutecore.api.data.FirstTickEvent;
import com.gtnewhorizons.mutecore.api.data.TickData;
import com.gtnewhorizons.mutecore.api.data.WorldStateValidator;
import com.gtnewhorizons.mutecore.api.gui.MuTEGUI;
import com.gtnewhorizons.mutecore.api.render.MuTERender;
import com.gtnewhorizons.mutecore.api.tile.MultiTileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dev.dominion.ecs.api.Entity;

public class MultiTileContainer {

    private final @Nonnull Class<? extends MultiTileEntity> clazz;
    private final int id;
    private final @Nonnull WeakReference<MultiTileEntityRegistry> reg;
    private final @Nonnull Entity originalEntity;
    private @Nonnull MuTEGUI gui;
    private String unlocalizedName;

    public MultiTileContainer(@Nonnull MultiTileEntityRegistry reg, int id,
        @Nonnull Class<? extends MultiTileEntity> clazz) {
        this.reg = new WeakReference<>(reg);
        this.clazz = clazz;
        this.id = id;
        originalEntity = MuTECore.ENGINE.createEntity(new FakeEntity());
    }

    public @Nonnull MultiTileContainer addComponents(Object... components) {
        for (Object component : components) {
            originalEntity.add(component);
        }
        return this;
    }

    public @Nonnull MultiTileContainer gui(@Nonnull MuTEGUI gui) {
        this.gui = gui;
        return this;
    }

    public @Nonnull MultiTileContainer unlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        return this;
    }

    public boolean register() {
        if (gui == null) throw new IllegalStateException("No gui was registered for entity with id: " + id);
        return reg.get() != null && reg.get()
            .register(id, this);
    }

    public @Nonnull Entity createNewEntity() {
        Entity newEntity = MuTECore.ENGINE.createEntityAs(originalEntity);
        newEntity.removeType(FakeEntity.class);
        newEntity.add(
            new Id(
                id,
                reg.get()
                    .getBlockId()));
        newEntity.add(new TickData());
        newEntity.add(new FirstTickEvent());
        return newEntity;
    }

    public @Nullable MultiTileEntity createNewTileEntity() {
        try {
            MultiTileEntity entity = clazz.newInstance();
            return entity;
        } catch (Exception ex) {
            MuTECore.LOG.error("Unable to create a new TileEntity for class: " + clazz.getName());
            return null;
        }
    }

    public @Nonnull Class<? extends MultiTileEntity> getTileEntityClass() {
        return clazz;
    }

    public @Nonnull MuTEGUI getGUI() {
        return gui;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof MultiTileContainer other)) return false;
        if (id != other.id) return false;
        return true;
    }

    public static class FakeEntity {
    }

    public static class Id implements WorldStateValidator {

        private int id;
        private int regId;

        public Id(int id, int regId) {
            this.id = id;
            this.regId = regId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRegId() {
            return regId;
        }

        public void setRegId(int regId) {
            this.regId = regId;
        }

        @Override
        public void save(NBTTagCompound nbt) {
            NBTTagCompound idNBT = new NBTTagCompound();
            idNBT.setInteger("i", id);
            idNBT.setInteger("r", regId);
            nbt.setTag("idData", idNBT);
        }

        @Override
        public void load(NBTTagCompound nbt) {
            NBTTagCompound idNBT = nbt.getCompoundTag("idData");
            id = idNBT.getInteger("i");
            regId = idNBT.getInteger("r");
        }
    }
}
