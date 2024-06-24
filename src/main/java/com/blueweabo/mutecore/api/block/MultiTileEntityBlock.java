package com.blueweabo.mutecore.api.block;

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

import com.blueweabo.mutecore.MuTECore;
import com.blueweabo.mutecore.MuTENetwork;
import com.blueweabo.mutecore.MuTENetwork.MuTEPacket;
import com.blueweabo.mutecore.api.data.Coordinates;
import com.blueweabo.mutecore.api.registry.EventRegistry;
import com.blueweabo.mutecore.api.registry.MultiTileEntityRegistry;
import com.blueweabo.mutecore.api.registry.PlayerInteractionEvent;
import com.blueweabo.mutecore.api.registry.MultiTileContainer.Id;
import com.blueweabo.mutecore.api.tile.MultiTileEntity;
import com.blueweabo.mutecore.client.MultiTileBlockRenderer;

import dev.dominion.ecs.api.Entity;

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
        Entity entity = ((MultiTileEntity) world.getTileEntity(x,y,z)).getEntity();
        return registry.getItemStack(entity.get(Id.class).getId());
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(worldIn, x, y, z, neighbor);
        TileEntity te = worldIn.getTileEntity(x, y, z);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
        TileEntity te = world.getTileEntity(x, y, z);
        TileEntity changed = world.getTileEntity(tileX, tileY, tileZ);
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (player instanceof EntityPlayerMP playermp) {
            MuTEPacket packet = new MuTEPacket();
            NBTTagCompound nbt = new NBTTagCompound();
            te.writeToNBT(nbt);
            packet.setComponentData(nbt);
            MuTENetwork.sendToPlayer(packet, playermp);
        }
        if (!(te instanceof MultiTileEntity mute)) return false;
        Object eventComponent = null;

        for (PlayerInteractionEvent preEvent : EventRegistry.PLAYER_INTERACTION_EVENTS) {
            eventComponent = preEvent.generate(player, mute.getEntity());
            if (eventComponent != null) break;
        }
        if (eventComponent == null) return false;
        if (mute.getEntity()
            .has(eventComponent.getClass())) return false;
        mute.getEntity()
            .add(eventComponent);
        return true;
    }

    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (!(te instanceof MultiTileEntity mute)) return;
        MuTECore.ENGINE.deleteEntity(mute.getEntity());
        super.breakBlock(worldIn, x, y, z, blockBroken, meta);
    }

    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (!(te instanceof MultiTileEntity mute)) return;
        mute.getEntity()
            .add(new Coordinates(x, y, z));
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
