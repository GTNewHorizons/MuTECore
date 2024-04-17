package com.blueweabo.mutecore.api.host;

import com.blueweabo.mutecore.api.logic.ItemInventoryLogic;
import com.cleanroommc.modularui.api.IItemStackLong;
import com.cleanroommc.modularui.utils.item.ItemStackLongDelegate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public interface ItemInventoryLogicHost extends ISidedInventory {

    default ItemInventoryLogic getItemLogic() {
        return getItemLogic(ForgeDirection.UNKNOWN);
    }

    ItemInventoryLogic getItemLogic(ForgeDirection side);

    @Override
    default boolean canExtractItem(int slot, ItemStack item, int side) {
        ItemInventoryLogic logic = getItemLogic(ForgeDirection.getOrientation(side));
        return logic != null && logic.extract(new ItemStackLongDelegate(item), item.stackSize, false) != null;
    }

    @Override
    default boolean canInsertItem(int slot, ItemStack item, int side) {
        ItemInventoryLogic logic = getItemLogic(ForgeDirection.getOrientation(side));
        return logic != null && logic.extract(new ItemStackLongDelegate(item), item.stackSize, false) != null;
    }

    @Override
    default int[] getAccessibleSlotsFromSide(int side) {
        ItemInventoryLogic logic = getItemLogic(ForgeDirection.getOrientation(side));
        if (logic == null) {
            return new int[0];
        }
        int arr[] = new int[logic.getSlots()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        return arr;
    }

    @Override
    default void closeInventory() {}

    @Override
    default ItemStack decrStackSize(int index, int count) {
        ItemInventoryLogic logic = getItemLogic();
        if (logic == null) return null;
        IItemStackLong stack = logic.get(index);
        if (stack == null) return null;
        IItemStackLong extractTry = logic.extract(stack, count, false);
        if (extractTry == null || extractTry.getStackSize() < count) return null;
        IItemStackLong extract = logic.extract(stack, count, true);
        return extract.getAsItemStack();
    }

    @Override
    default String getInventoryName() {
        ItemInventoryLogic logic = getItemLogic();
        if (logic == null) return "";
        return logic.getName();
    }

    @Override
    default int getInventoryStackLimit() {
        ItemInventoryLogic logic = getItemLogic();
        if (logic == null) return 0;
        return Integer.MAX_VALUE;
    }

    @Override
    default int getSizeInventory() {
        ItemInventoryLogic logic = getItemLogic();
        if (logic == null) return 0;
        return logic.getSlots();
    }

    @Override
    default ItemStack getStackInSlot(int slotIn) {
        ItemInventoryLogic logic = getItemLogic();
        if (logic == null) return null;
        return logic.get(slotIn) == null ? null : logic.get(slotIn).getAsItemStack();
    }

    @Override
    default ItemStack getStackInSlotOnClosing(int index) {
        ItemInventoryLogic logic = getItemLogic();
        if (logic == null) return null;
        return logic.get(index) == null ? null : logic.get(index).getAsItemStack();
    }

    @Override
    default boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    default boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    default boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    default void markDirty() {}

    @Override
    default void openInventory() {}

    @Override
    default void setInventorySlotContents(int index, ItemStack stack) {}

}
