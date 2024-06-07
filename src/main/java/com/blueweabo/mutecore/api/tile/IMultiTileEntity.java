package com.blueweabo.mutecore.api.tile;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IMultiTileEntity {

    @Nonnull
    ForgeDirection getFacing();

    @Nonnull
    World getWorld();

    int getXCoord();

    int getYCoord();

    int getZCoord();

    void onBlockPlaced();

    void onBlockBroken();

    void onNeighborBlockChange(@Nonnull Block neighbor);

    void onNeighborChange(@Nonnull IMultiTileEntity muteChanged);

    boolean onBlockActivated(@Nonnull EntityPlayer player, @Nonnull ForgeDirection side, float subX, float subY,
        float subZ);
}
