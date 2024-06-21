package com.blueweabo.mutecore.api.tile;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.blueweabo.mutecore.MuTECore;
import com.blueweabo.mutecore.MuTENetwork;
import com.blueweabo.mutecore.MuTENetwork.MuTEPacket;
import com.blueweabo.mutecore.api.block.MultiTileEntityBlock;
import com.blueweabo.mutecore.api.data.Coordinates;
import com.blueweabo.mutecore.api.data.WorldContainer;
import com.blueweabo.mutecore.api.data.WorldStateValidator;
import com.blueweabo.mutecore.api.gui.ComponentData;
import com.blueweabo.mutecore.api.registry.MultiTileContainer;
import com.blueweabo.mutecore.api.registry.MultiTileEntityRegistry;
import com.blueweabo.mutecore.api.registry.MultiTileContainer.Id;
import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.GuiSyncManager;
import com.cleanroommc.modularui.widgets.ButtonWidget;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import dev.dominion.ecs.api.Entity;
import dev.dominion.ecs.engine.IntEntity;

public class MultiTileEntity extends TileEntity implements IGuiHolder<ComponentData>{

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
        MuTECore.LOG.info("reading nbt");
        MultiTileEntityRegistry registry = ((MultiTileEntityBlock) getWorldObj().getBlock(xCoord, yCoord, zCoord)).getRegistry();
        MultiTileContainer container = registry.getMultiTileContainer(nbt.getInteger("id"));
        entity = container.createNewEntity();
        entity.add(new Coordinates(xCoord, yCoord, zCoord));
        entity.add(new WorldContainer(getWorldObj()));
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
        MuTECore.LOG.info("writing nbt");
        nbt.setInteger("id", entity.get(Id.class).getId());
        Object[] components = ((IntEntity) entity).getComponentArray();
        for (Object component : components) {
            if (component instanceof WorldStateValidator validator) {
                validator.save(nbt);
            }
        }
    }

}
