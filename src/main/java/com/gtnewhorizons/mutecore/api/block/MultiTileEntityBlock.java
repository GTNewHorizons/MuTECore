package com.gtnewhorizons.mutecore.api.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.gtnewhorizons.mutecore.MuTECore;
import com.gtnewhorizons.mutecore.MuTENetwork;
import com.gtnewhorizons.mutecore.MuTENetwork.MuTEPacket;
import com.gtnewhorizons.mutecore.api.data.Coordinates;
import com.gtnewhorizons.mutecore.api.data.WorldContainer;
import com.gtnewhorizons.mutecore.api.event.BlockBreakEvent;
import com.gtnewhorizons.mutecore.api.event.BlockPlaceEvent;
import com.gtnewhorizons.mutecore.api.event.NeighborBlockChangeEvent;
import com.gtnewhorizons.mutecore.api.event.NeighborTileChangeEvent;
import com.gtnewhorizons.mutecore.api.event.PlayerInteractionEvent;
import com.gtnewhorizons.mutecore.api.registry.EventRegistry;
import com.gtnewhorizons.mutecore.api.registry.MultiTileContainer.Id;
import com.gtnewhorizons.mutecore.api.registry.MultiTileEntityRegistry;
import com.gtnewhorizons.mutecore.api.tile.MultiTileEntity;
import com.gtnewhorizons.mutecore.client.MultiTileBlockRenderer;

public class MultiTileEntityBlock extends BlockContainer {

    MultiTileEntityRegistry registry;

    public MultiTileEntityBlock(Material material) {
        super(material);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return registry.createNewTileEntity(meta);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        list.addAll(registry.getAllItemStacks());
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        Entity entity = ((MultiTileEntity) world.getTileEntity(x, y, z)).getEntity();
        return registry.getItemStack(
            entity.getComponent(Id.class)
                .getId());
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(worldIn, x, y, z, neighbor);
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (!(te instanceof MultiTileEntity mute)) return;
        Entity entity = mute.getEntity();
        for (NeighborBlockChangeEvent preEvent : EventRegistry.NEIGHBOR_BLOCK_CHANGE_EVENTS) {
            preEvent.call(neighbor, x, y, z, entity);
        }
    }

    @Override
    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
        TileEntity te = world.getTileEntity(x, y, z);
        TileEntity changed = world.getTileEntity(tileX, tileY, tileZ);
        if (!(te instanceof MultiTileEntity mute)) return;
        Entity entity = mute.getEntity();
        for (NeighborTileChangeEvent preEvent : EventRegistry.NEIGHBOR_TILE_CHANGE_EVENTS) {
            preEvent.call(changed, entity);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (!(te instanceof MultiTileEntity mute)) return false;
        if (player instanceof EntityPlayerMP playermp) {
            MuTEPacket packet = new MuTEPacket();
            NBTTagCompound nbt = new NBTTagCompound();
            te.writeToNBT(nbt);
            packet.setComponentData(nbt);
            MuTENetwork.sendToPlayer(packet, playermp);
        }
        Component eventComponent = null;
        for (PlayerInteractionEvent preEvent : EventRegistry.PLAYER_INTERACTION_EVENTS) {
            eventComponent = preEvent.generate(player, mute.getEntity());
            if (eventComponent != null) break;
        }
        if (eventComponent == null) return false;
        if (mute.getEntity()
            .getComponent(eventComponent.getClass()) != null) return false;
        mute.getEntity()
            .add(eventComponent);
        return true;
    }

    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (!(te instanceof MultiTileEntity mute)) return;
        Entity entity = mute.getEntity();
        for (BlockBreakEvent preEvent : EventRegistry.BLOCK_BREAK_EVENTS) {
            preEvent.call(entity);
        }
        MuTECore.ENGINE.removeEntity(entity);
        super.breakBlock(worldIn, x, y, z, blockBroken, meta);
    }

    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (!(te instanceof MultiTileEntity mute)) return;
        Entity entity = mute.getEntity();
        entity.add(new Coordinates(x, y, z));
        entity.add(new WorldContainer(worldIn));
        for (BlockPlaceEvent preEvent : EventRegistry.BLOCK_PLACE_EVENTS) {
            preEvent.call(entity);
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return super.getDrops(world, x, y, z, metadata, fortune);
    }

    public MultiTileEntityRegistry getRegistry() {
        return registry;
    }

    @Internal
    public void setRegistry(MultiTileEntityRegistry registry) {
        this.registry = registry;
    }

    @Override
    public int getRenderType() {
        return MultiTileBlockRenderer.INSTANCE.getRenderId();
    }

}
