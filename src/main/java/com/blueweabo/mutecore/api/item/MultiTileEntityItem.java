package com.blueweabo.mutecore.api.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import com.blueweabo.mutecore.api.block.MultiTileEntityBlock;

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
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return block.getRegistry()
            .getMultiTileContainer(Items.feather.getDamage(stack))
            .getUnlocalizedName();
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
