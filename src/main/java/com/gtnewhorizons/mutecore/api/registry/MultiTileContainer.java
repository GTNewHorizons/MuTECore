package com.gtnewhorizons.mutecore.api.registry;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.gtnewhorizons.mutecore.MuTECore;
import com.gtnewhorizons.mutecore.api.data.FirstTickEvent;
import com.gtnewhorizons.mutecore.api.data.WorldStateValidator;
import com.gtnewhorizons.mutecore.api.gui.MuTEGUI;
import com.gtnewhorizons.mutecore.api.item.TooltipAssigner;
import com.gtnewhorizons.mutecore.api.tile.MultiTileEntity;

public class MultiTileContainer {

    private final @Nonnull Class<? extends MultiTileEntity> clazz;
    private final int id;
    private final @Nonnull WeakReference<MultiTileEntityRegistry> reg;
    private @Nonnull ComponentsCreator componentsCreator;
    private final @Nonnull Entity fakeEntity;
    private Class<? extends TooltipAssigner> tooltipClass;
    private @Nonnull MuTEGUI gui;
    private String unlocalizedName;

    public MultiTileContainer(@Nonnull MultiTileEntityRegistry reg, int id,
        @Nonnull Class<? extends MultiTileEntity> clazz) {
        this.reg = new WeakReference<>(reg);
        this.clazz = clazz;
        this.id = id;
        fakeEntity = new Entity();
    }

    public @Nonnull MultiTileContainer componentsCreator(ComponentsCreator componentsCreator) {
        this.componentsCreator = componentsCreator;
        List<Component> components = this.componentsCreator.createComponents();
        for (int i = 0; i < components.size(); i++) {
            fakeEntity.add(components.get(i));
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

    public @Nonnull MultiTileContainer tooltipClass(Class<? extends TooltipAssigner> tooltipClass) {
        this.tooltipClass = tooltipClass;
        return this;
    }

    public boolean register() {
        if (gui == null) throw new IllegalStateException("No gui was registered for entity with id: " + id);
        return reg.get() != null && reg.get()
            .register(id, this);
    }

    public @Nonnull Entity createNewEntity() {
        List<? extends Component> components = componentsCreator.createComponents();
        Entity newEntity = new Entity();
        for (int i = 0; i < components.size(); i++) {
            newEntity.add(components.get(i));
        }
        newEntity.add(
            new Id(
                id,
                reg.get()
                    .getBlockId()));
        newEntity.add(new FirstTickEvent());
        MuTECore.ENGINE.addEntity(newEntity);
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

    public Class<? extends TooltipAssigner> getTooltipClass() {
        return tooltipClass;
    }

    public Entity getFakeEntity() {
        return fakeEntity;
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

    public static class FakeEntity implements Component {
    }

    public static class Id implements Component, WorldStateValidator {

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
            idNBT.setInteger("r", regId);
            idNBT.setInteger("i", id);
            nbt.setTag("idData", idNBT);
        }

        @Override
        public void load(NBTTagCompound nbt) {
            NBTTagCompound idNBT = nbt.getCompoundTag("idData");
            regId = idNBT.getInteger("r");
            id = idNBT.getInteger("i");
        }
    }
}
