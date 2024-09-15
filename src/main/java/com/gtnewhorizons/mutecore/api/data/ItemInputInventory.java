package com.gtnewhorizons.mutecore.api.data;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

import com.cleanroommc.modularui.utils.item.IItemStackLong;
import com.cleanroommc.modularui.utils.item.ItemStackLong;
import com.gtnewhorizons.mutecore.api.inventory.ItemInventory;

public class ItemInputInventory implements ItemInventory, WorldStateValidator {

    private IItemStackLong[] inv;
    private long limit;
    private UUID key;

    public ItemInputInventory(Integer size, Long limit) {
        this((int) size, (long) limit);
    }

    public ItemInputInventory(int size, long limit) {
        inv = new IItemStackLong[size];
        for (int i = 0; i < size; i++) {
            inv[i] = null;
        }
        key = UUID.randomUUID();
    }

    @Override
    public IItemStackLong get(int index) {
        return inv[index];
    }

    @Override
    public void set(int index, IItemStackLong item) {
        inv[index] = item;
    }

    @Override
    public int getSize() {
        return inv.length;
    }

    @Override
    public long getLimit() {
        return limit;
    }

    @Override
    public void save(NBTTagCompound nbt) {
        NBTTagCompound itemInInv = new NBTTagCompound();
        itemInInv.setString("key", key.toString());
        NBTTagList invNBT = new NBTTagList();
        for (int i = 0; i < getSize(); i++) {
            IItemStackLong stack = get(i);
            if (stack == null) continue;
            NBTTagCompound stackNBT = new NBTTagCompound();
            stackNBT.setInteger("slot", i);
            stack.writeToNBT(stackNBT);
            invNBT.appendTag(stackNBT);
        }
        itemInInv.setTag("inv", invNBT);
        nbt.setTag("itemInInv", itemInInv);
    }

    @Override
    public void load(NBTTagCompound nbt) {
        NBTTagCompound itemInInv = nbt.getCompoundTag("itemInInv");
        key = UUID.fromString(itemInInv.getString("key"));
        NBTTagList invNBT = itemInInv.getTagList("inv", NBT.TAG_LIST);
        for (int i = 0; i < invNBT.tagCount(); i++) {
            NBTTagCompound stackNBT = invNBT.getCompoundTagAt(i);
            inv[stackNBT.getInteger("slot")] = ItemStackLong.loadFromNBT(stackNBT);
        }
    }

}
