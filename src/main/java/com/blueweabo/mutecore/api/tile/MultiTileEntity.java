package com.blueweabo.mutecore.api.tile;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import dev.dominion.ecs.api.Entity;

public class MultiTileEntity extends TileEntity implements IMultiTileEntity {

    private @Nonnull ForgeDirection facing = ForgeDirection.WEST;
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
    public final ForgeDirection getFacing() {
        return facing;
    }

    @Override
    public final World getWorld() {
        return getWorldObj();
    }

    @Override
    public int getXCoord() {
        return xCoord;
    }

    @Override
    public int getYCoord() {
        return yCoord;
    }

    @Override
    public int getZCoord() {
        return zCoord;
    }

    @Override
    public void onBlockPlaced() {}

    @Override
    public void onBlockBroken() {}

    @Override
    public void onNeighborBlockChange(@Nonnull Block neighbor) {}

    @Override
    public void onNeighborChange(@Nonnull IMultiTileEntity muteChanged) {}

    @Override
    public boolean onBlockActivated(@Nonnull EntityPlayer player, @Nonnull ForgeDirection side, float subX, float subY,
        float subZ) {
        return false;
    }

}
