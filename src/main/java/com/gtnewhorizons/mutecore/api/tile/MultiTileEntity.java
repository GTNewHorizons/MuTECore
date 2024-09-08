package com.gtnewhorizons.mutecore.api.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.gtnewhorizons.mutecore.MuTECore;
import com.gtnewhorizons.mutecore.MuTENetwork;
import com.gtnewhorizons.mutecore.MuTENetwork.MuTEPacket;
import com.gtnewhorizons.mutecore.api.block.MultiTileEntityBlock;
import com.gtnewhorizons.mutecore.api.data.Coordinates;
import com.gtnewhorizons.mutecore.api.data.WorldContainer;
import com.gtnewhorizons.mutecore.api.data.WorldStateValidator;
import com.gtnewhorizons.mutecore.api.gui.ComponentData;
import com.gtnewhorizons.mutecore.api.registry.MultiTileContainer;
import com.gtnewhorizons.mutecore.api.registry.MultiTileContainer.Id;
import com.gtnewhorizons.mutecore.api.registry.MultiTileEntityRegistry;

import dev.dominion.ecs.api.Entity;
import dev.dominion.ecs.engine.IntEntity;

public class MultiTileEntity extends TileEntity implements IGuiHolder<ComponentData> {

    private Entity entity;

    public MultiTileEntity() {}

    public void setEntity(Entity entity) {
        if (this.entity != null) {
            MuTECore.ENGINE.deleteEntity(this.entity);
        }
        this.entity = entity;
    }

    @Override
    public final boolean canUpdate() {
        return false;
    }

    @Override
    public Packet getDescriptionPacket() {
        MuTEPacket packet = new MuTEPacket();
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        packet.setComponentData(nbt);
        MuTENetwork.sentToPlayersInRange(packet, getWorldObj(), xCoord, zCoord);
        return super.getDescriptionPacket();
    }

    @Internal
    public Entity getEntity() {
        return entity;
    }

    @Override
    public ModularPanel buildUI(ComponentData data, PanelSyncManager syncManager) {
        MultiTileEntityRegistry reg = ((MultiTileEntityBlock) data.getWorld()
            .getBlock(xCoord, yCoord, zCoord)).getRegistry();
        MultiTileContainer container = reg.getMultiTileContainer(
            entity.get(Id.class)
                .getId());
        if (entity == null) {
            entity = container.createNewEntity();
        }
        if (!entity.has(WorldContainer.class)) {
            entity.add(new WorldContainer(syncManager.getPlayer().worldObj));
        }
        Object[] components = ((IntEntity) entity).getComponentArray();
        for (Object component : components) {
            if (component instanceof WorldStateValidator validator) {
                validator.load(data.getComponentData());
            }
        }
        return container.getGUI()
            .createGUI(entity, syncManager);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagCompound idData = nbt.getCompoundTag("idData");
        MultiTileEntityRegistry registry = MultiTileEntityRegistry.getRegistry(idData.getInteger("r"));
        MultiTileContainer container = registry.getMultiTileContainer(idData.getInteger("i"));
        entity = container.createNewEntity();
        entity.add(new WorldContainer(getWorldObj()));
        entity.add(new Coordinates(xCoord, yCoord, zCoord));
        Object[] components = ((IntEntity) entity).getComponentArray();
        for (Object component : components) {
            if (component instanceof WorldStateValidator validator) {
                validator.load(nbt);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        Object[] components = ((IntEntity) entity).getComponentArray();
        for (Object component : components) {
            if (component instanceof WorldStateValidator validator) {
                validator.save(nbt);
            }
        }
    }

}
