package com.blueweabo.mutecore.api.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.blueweabo.mutecore.api.tile.base.TickableMultiTileEntity;
import com.blueweabo.mutecore.client.MachineGuiFactory;
import com.cleanroommc.modularui.ModularUI;
import com.cleanroommc.modularui.api.UIFactory;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.factory.TileEntityGuiFactory;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Entity;
import dev.dominion.ecs.api.Scheduler;
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
