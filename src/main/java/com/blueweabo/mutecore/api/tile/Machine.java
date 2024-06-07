package com.blueweabo.mutecore.api.tile;

import dev.dominion.ecs.api.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class Machine extends MultiTileEntity implements IMachine {

    Entity entity;

    public Machine() {
        super();
    }

    @Override
    public boolean onBlockActivated(EntityPlayer player, ForgeDirection side, float subX, float subY, float subZ) {
        if (super.onBlockActivated(player, side, subX, subY, subZ)) return true;
        return true;
    }

}
