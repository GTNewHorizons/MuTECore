package com.blueweabo.mutecore.api.block;

import java.util.ArrayList;
import java.util.List;

import com.blueweabo.mutecore.api.registry.MultiTileEntityRegistry;
import com.blueweabo.mutecore.api.tile.base.IMultiTileEntity;
import com.blueweabo.mutecore.api.tile.base.MultiTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MultiTileEntityBlock extends BlockContainer {

    MultiTileEntityRegistry registry;

    protected MultiTileEntityBlock(Material material) {
        super(material);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null; //return registry.createTileEntity(meta);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        return super.getPickBlock(target, world, x, y, z, player);
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(worldIn, x, y, z, neighbor);
        TileEntity te = worldIn.getTileEntity(x,y,z);
        if (!(te instanceof IMultiTileEntity mute)) return;
        mute.onNeighborBlockChange(neighbor);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
        TileEntity te = world.getTileEntity(x,y,z);
        TileEntity changed = world.getTileEntity(tileX, tileY, tileZ);
        if (!(te instanceof IMultiTileEntity mute) || !(changed instanceof IMultiTileEntity changedMute)) return;
        mute.onNeighborChange(changedMute);
    }


    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        TileEntity te = worldIn.getTileEntity(x,y,z);
        if (!(te instanceof IMultiTileEntity mute)) return false;
        return mute.onBlockActivated(player, ForgeDirection.getOrientation(side) , subX, subY, subZ);
    }

    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta) {
        TileEntity te = worldIn.getTileEntity(x,y,z);
        if (!(te instanceof IMultiTileEntity mute)) {
            super.breakBlock(worldIn, x, y, z, blockBroken, meta);
            return;
        }
        mute.onBlockBroken();
        super.breakBlock(worldIn, x, y, z, blockBroken, meta);
    }

    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
        TileEntity te = worldIn.getTileEntity(x,y,z);
        if (!(te instanceof IMultiTileEntity mute)) return;
        mute.onBlockPlaced();
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return super.getDrops(world, x, y, z, metadata, fortune);
    }
}
