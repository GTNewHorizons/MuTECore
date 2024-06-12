package com.blueweabo.mutecore.api.tile;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.GuiSyncManager;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import dev.dominion.ecs.api.Entity;

public class MultiTileEntity extends TileEntity implements IGuiHolder<PosGuiData>{

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

    @Internal
    public Entity getEntity() {
        return entity;
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, GuiSyncManager syncManager) {
        return new ModularPanel("test");
    }

}
