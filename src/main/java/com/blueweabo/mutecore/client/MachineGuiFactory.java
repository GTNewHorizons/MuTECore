package com.blueweabo.mutecore.client;

import java.util.Objects;

import javax.annotation.Nonnull;

import com.blueweabo.mutecore.api.host.GUIHost;
import com.blueweabo.mutecore.api.tile.machine.Machine;
import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.AbstractUIFactory;
import com.cleanroommc.modularui.factory.GuiManager;
import com.cleanroommc.modularui.factory.PosGuiData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class MachineGuiFactory<D extends PosGuiData> extends AbstractUIFactory<D> {

    public static final MachineGuiFactory<PosGuiData> INSTANCE = new MachineGuiFactory<>();

    private MachineGuiFactory() {
        super("mute:machine");
    }

    public static <T extends Machine<?,D>, D extends PosGuiData> void open(EntityPlayer player, T tile) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(tile);
        if (tile.isInvalid()) {
            throw new IllegalArgumentException("Can't open invalid TileEntity GUI!");
        }
        if (player.worldObj != tile.getWorld()) {
            throw new IllegalArgumentException("TileEntity must be in same dimension as the player!");
        }

        GuiManager.open(INSTANCE, tile.getGuiData(), (EntityPlayerMP) player);
    }

    @Override
    public @Nonnull IGuiHolder<D> getGuiHolder(PosGuiData data) {
        return Objects.requireNonNull(castGuiHolder(data.getTileEntity()), "Found TileEntity is not a gui holder!");
    }

    @Override
    public void writeGuiData(D guiData, PacketBuffer buffer) {
        buffer.writeVarIntToBuffer(guiData.getX());
        buffer.writeVarIntToBuffer(guiData.getY());
        buffer.writeVarIntToBuffer(guiData.getZ());
        TileEntity te = guiData.getTileEntity();
        if (!(te instanceof GUIHost<?> host)) return;
        host.writeGuiData(guiData, buffer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nonnull D readGuiData(EntityPlayer player, PacketBuffer buffer) {
        TileEntity te = player.worldObj.getTileEntity(buffer.readVarIntFromBuffer(), buffer.readVarIntFromBuffer(), buffer.readVarIntFromBuffer());
        if (!(te instanceof GUIHost<?>)) return (D) new PosGuiData(player, te.xCoord, te.yCoord, te.zCoord);
        try {
            GUIHost<D> host = (GUIHost<D>) te;
            return host.readGuiData(buffer);
        } catch (ClassCastException ex) {
            return (D) new PosGuiData(player, te.xCoord, te.yCoord, te.zCoord);
        }
    }
}
