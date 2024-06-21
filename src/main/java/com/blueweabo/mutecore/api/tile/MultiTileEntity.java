package com.blueweabo.mutecore.api.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.blueweabo.mutecore.MuTENetwork;
import com.blueweabo.mutecore.MuTENetwork.MuTEPacket;
import com.blueweabo.mutecore.api.data.Coordinates;
import com.blueweabo.mutecore.api.data.WorldContainer;
import com.blueweabo.mutecore.api.data.WorldStateValidator;
import com.blueweabo.mutecore.api.gui.ComponentData;
import com.blueweabo.mutecore.api.registry.MultiTileContainer;
import com.blueweabo.mutecore.api.registry.MultiTileEntityRegistry;
import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.GuiSyncManager;

import dev.dominion.ecs.api.Entity;
import dev.dominion.ecs.engine.IntEntity;

public class MultiTileEntity extends TileEntity implements IGuiHolder<ComponentData> {

    private Entity entity;

    public MultiTileEntity() {}

    public void setEntity(Entity entity) {
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
    public ModularPanel buildUI(ComponentData data, GuiSyncManager syncManager) {
        return new ModularPanel("test");
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
