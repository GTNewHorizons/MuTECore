package com.blueweabo.mutecore.api.gui;

import java.io.IOException;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import com.blueweabo.mutecore.MuTECore;
import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.AbstractUIFactory;
import com.cleanroommc.modularui.factory.GuiManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class MultiTileEntityGuiFactory extends AbstractUIFactory<ComponentData> {

    public static MultiTileEntityGuiFactory INSTANCE = new MultiTileEntityGuiFactory();

    private MultiTileEntityGuiFactory() {
        super("mutecore:gui");
    }

    public static void open(@Nonnull EntityPlayer player, int x, int y, int z, @Nonnull NBTTagCompound componentData) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(componentData);
        ComponentData data = new ComponentData(player, x, y, z, componentData);
        GuiManager.open(INSTANCE, data, (EntityPlayerMP) player);
    }

    @Override
    public void writeGuiData(ComponentData guiData, PacketBuffer buffer) {
        buffer.writeVarIntToBuffer(guiData.getX());
        buffer.writeVarIntToBuffer(guiData.getY());
        buffer.writeVarIntToBuffer(guiData.getZ());
        try {
            buffer.writeNBTTagCompoundToBuffer(guiData.getComponentData());
        } catch (IOException ex) {
            MuTECore.LOG.error("Couldn't write component data for GUI");
            MuTECore.LOG.error(ex.toString());
        }
    }

    @Override
    public @NotNull ComponentData readGuiData(EntityPlayer player, PacketBuffer buffer) {
        int x = buffer.readVarIntFromBuffer();
        int y = buffer.readVarIntFromBuffer();
        int z = buffer.readVarIntFromBuffer();
        NBTTagCompound componentData;
        try {
            componentData = buffer.readNBTTagCompoundFromBuffer();
        } catch (IOException ex) {
            MuTECore.LOG.error("Couldn't read component data for GUI");
            MuTECore.LOG.error(ex.toString());
            componentData = new NBTTagCompound();
        }
        return new ComponentData(player, x, y, z, componentData);
    }

    @Override
    public @NotNull IGuiHolder<ComponentData> getGuiHolder(ComponentData data) {
        return Objects.requireNonNull(castGuiHolder(data.getTileEntity()));
    }


}
