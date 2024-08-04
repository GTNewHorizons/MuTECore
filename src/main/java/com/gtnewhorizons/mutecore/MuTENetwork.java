package com.gtnewhorizons.mutecore;

import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import org.jetbrains.annotations.Nullable;

import com.cleanroommc.modularui.network.IPacket;
import com.gtnewhorizons.mutecore.api.block.MultiTileEntityBlock;
import com.gtnewhorizons.mutecore.api.data.Coordinates;
import com.gtnewhorizons.mutecore.api.data.WorldContainer;
import com.gtnewhorizons.mutecore.api.data.WorldStateValidator;
import com.gtnewhorizons.mutecore.api.registry.MultiTileContainer;
import com.gtnewhorizons.mutecore.api.registry.MultiTileEntityRegistry;
import com.gtnewhorizons.mutecore.api.tile.MultiTileEntity;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dev.dominion.ecs.api.Entity;
import dev.dominion.ecs.engine.IntEntity;

public class MuTENetwork {

    private static SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(MuTECore.MODID);
    private static int packetId = 0;

    public static void init() {
        registerS2C(MuTEPacket.class);
    }

    private static void registerC2S(Class<? extends IPacket> clazz) {
        CHANNEL.registerMessage(C2SHandler, clazz, packetId++, Side.SERVER);
    }

    private static void registerS2C(Class<? extends IPacket> clazz) {
        CHANNEL.registerMessage(S2CHandler, clazz, packetId++, Side.CLIENT);
    }

    public static void sendToServer(IPacket packet) {
        CHANNEL.sendToServer(packet);
    }

    public static void sendToWorld(IPacket packet, World world) {
        CHANNEL.sendToDimension(packet, world.provider.dimensionId);
    }

    public static void sendToPlayer(IPacket packet, EntityPlayerMP player) {
        CHANNEL.sendTo(packet, player);
    }

    public static void sentToPlayersInRange(IPacket packet, World world, int x, int z) {
        if (!world.isRemote) {
            for (EntityPlayer p : world.playerEntities) {
                if (!(p instanceof EntityPlayerMP player)) {
                    continue;
                }
                Chunk tChunk = world.getChunkFromBlockCoords(x, z);
                if (player.getServerForPlayer()
                    .getPlayerManager()
                    .isPlayerWatchingChunk(player, tChunk.xPosition, tChunk.zPosition)) {
                    sendToPlayer(packet, player);
                }
            }
        }
    }

    final static IMessageHandler<IPacket, IPacket> S2CHandler = (message, ctx) -> {
        NetHandlerPlayClient handler = ctx.getClientHandler();
        return message.executeClient(handler);
    };

    final static IMessageHandler<IPacket, IPacket> C2SHandler = (message, ctx) -> {
        NetHandlerPlayServer handler = ctx.getServerHandler();
        return message.executeServer(handler);
    };

    public static class MuTEPacket implements IPacket {

        NBTTagCompound data;

        public void setComponentData(NBTTagCompound data) {
            this.data = data;
        }

        @Override
        public void write(PacketBuffer buf) throws IOException {
            buf.writeNBTTagCompoundToBuffer(data);
        }

        @Override
        public void read(PacketBuffer buf) throws IOException {
            data = buf.readNBTTagCompoundFromBuffer();
        }

        @Override
        public @Nullable IPacket executeClient(NetHandlerPlayClient handler) {
            Coordinates coords = new Coordinates(0, 0, 0);
            coords.load(data);
            IBlockAccess world = Minecraft.getMinecraft().theWorld;
            Block bl = world.getBlock(coords.getX(), coords.getY(), coords.getZ());
            if (!(bl instanceof MultiTileEntityBlock mbl)) return null;
            TileEntity te = world.getTileEntity(coords.getX(), coords.getY(), coords.getZ());
            if (!(te instanceof MultiTileEntity mute)) return null;
            MultiTileEntityRegistry registry = mbl.getRegistry();
            MultiTileContainer container = registry.getMultiTileContainer(
                data.getCompoundTag("idData")
                    .getInteger("i"));
            Entity entity = container.createNewEntity();
            entity.add(coords);
            entity.add(new WorldContainer(Minecraft.getMinecraft().theWorld));
            Object[] components = ((IntEntity) entity).getComponentArray();
            for (int i = 0; i < components.length; i++) {
                if (!(components[i] instanceof WorldStateValidator validator)) continue;
                validator.load(data);
            }
            mute.setEntity(entity);
            return null;
        }
    }
}
