package com.gtnewhorizons.mutecore.api.inventory;

import com.cleanroommc.modularui.api.IItemStackLong;
import com.cleanroommc.modularui.utils.item.IItemHandlerLong;
import com.cleanroommc.modularui.utils.item.ItemHandlerHelper;

public class ItemComponentInventoryHandler implements IItemHandlerLong {

    private ItemInventory inventory;

    public ItemComponentInventoryHandler(ItemInventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getSlots() {
        return inventory.getSize();
    }

    @Override
    public void setStackInSlotLong(int slot, IItemStackLong stack) {
        inventory.set(slot, stack);
    }

    @Override
    public IItemStackLong extractItemLong(int slot, long amount, boolean simulate) {
        IItemStackLong original = inventory.get(slot);
        if (original == null) return null;
        IItemStackLong stack = original.copy();
        stack.setStackSize(Math.min(stack.getStackSize(), amount));
        if (simulate) {
            return stack;
        }
        original.setStackSize(original.getStackSize() - stack.getStackSize());
        setStackInSlotLong(slot, original);
        return stack;
    }

    @Override
    public long getSlotLimitLong(int slot) {
        return inventory.getLimit();
    }

    @Override
    public IItemStackLong getStackInSlotLong(int slot) {
        return inventory.get(slot);
    }

    @Override
    public IItemStackLong insertItemLong(int slot, IItemStackLong stack, boolean simulate) {
        if (stack == null) return null;
        stack = stack.copy();
        IItemStackLong original = inventory.get(slot);
        if (original == null) {
            original = stack.copy();
            original.setStackSize(0);
        }
        if (!ItemHandlerHelper.canItemStacksStack(original, stack)) return stack;
        long amountToInsert = Math.min(stack.getStackSize(), getSlotLimitLong(slot) - original.getStackSize());
        stack.setStackSize(stack.getStackSize() - amountToInsert);
        if (simulate) {
            return stack;
        }
        original.setStackSize(original.getStackSize() + amountToInsert);
        setStackInSlotLong(slot, original);
        return stack;
    }

}
