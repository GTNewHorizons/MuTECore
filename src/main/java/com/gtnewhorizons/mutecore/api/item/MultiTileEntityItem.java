package com.gtnewhorizons.mutecore.api.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import com.badlogic.ashley.core.Entity;
import com.gtnewhorizons.mutecore.api.block.MultiTileEntityBlock;
import com.gtnewhorizons.mutecore.api.data.PlayerUUID;
import com.gtnewhorizons.mutecore.api.data.WorldContainer;
import com.gtnewhorizons.mutecore.api.registry.MultiTileContainer;
import com.gtnewhorizons.mutecore.api.tile.MultiTileEntity;

public class MultiTileEntityItem extends ItemBlock implements IFluidContainerItem {

    private final MultiTileEntityBlock block;

    public MultiTileEntityItem(Block block) {
        super(block);
        setHasSubtypes(true);
        setMaxDamage(0);
        if (!(block instanceof MultiTileEntityBlock muteBlock)) {
            throw new IllegalArgumentException("The block should extend MultiTileEntityBlock or be a variation of it");
        }
        this.block = muteBlock;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> tooltip, boolean f3h) {
        super.addInformation(itemStack, player, tooltip, f3h);
        MultiTileContainer container = block.getRegistry()
            .getMultiTileContainer(itemStack.getItemDamage());
        Class<? extends TooltipAssigner> toolTipClass = container.getTooltipClass();
        if (toolTipClass == null) return;
        Entity entity = container.getFakeEntity();
        TooltipAssigner tooltipData = entity.getComponent(toolTipClass);
        tooltipData.assignTooltip(tooltip);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return block.getRegistry()
            .getMultiTileContainer(Items.feather.getDamage(stack))
            .getUnlocalizedName();
    }

    @Override
    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float xF,
        float yF, float zF) {

        boolean status = super.onItemUse(item, player, world, x, y, z, side, xF, yF, zF);
        if (status) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (!(te instanceof MultiTileEntity mute)) return status;
            Entity entity = mute.getEntity();
            entity.add(new PlayerUUID(player.getUniqueID()));
            entity.add(new WorldContainer(world));
        }
        return status;
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public FluidStack getFluid(ItemStack container) {
        return null;
    }

    @Override
    public int getCapacity(ItemStack container) {
        return 0;
    }

    @Override
    public int fill(ItemStack container, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
        return null;
    }
}
