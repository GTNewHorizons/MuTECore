package com.blueweabo.mutecore.api.logic;

import java.util.UUID;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.blueweabo.mutecore.api.logic.interfaces.InventoryLogic;
import com.cleanroommc.modularui.api.IItemStackLong;
import com.cleanroommc.modularui.future.IItemHandlerLong;
import com.cleanroommc.modularui.future.ItemStackLongHandler;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;

@ParametersAreNonnullByDefault
public class ItemInventoryLogic implements InventoryLogic<IItemStackLong> {

    private String name = "";
    private UUID id = UUID.randomUUID();
    private IItemHandlerLong handler;
    private Object2LongMap<IItemStackLong> quickAccess;

    public ItemInventoryLogic(int size) {
        this(size, 64);
    }

    public ItemInventoryLogic(int size, long capacity) {
        this(new ItemStackLongHandler(size, capacity));
    }

    public ItemInventoryLogic(IItemHandlerLong handler) {
        this.handler = handler;
        quickAccess = new Object2LongOpenHashMap<>(handler.getSlots());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public @Nullable IItemStackLong extract(IItemStackLong request, long amount, boolean doExtract) {
        if (amount <= 0) return null;
        request = request.copy();
        request.setStackSize(1);
        int slot = (int)quickAccess.getOrDefault(request, -1);
        if (slot >= 0 && request.isItemEqual(handler.getStackInSlotLong(slot))) {
            return handler.extractItemLong(slot, amount, doExtract);
        }
        slot = findSlotFromItem(request);
        if (slot < 0) return null;
        quickAccess.put(request, slot);
        return handler.extractItemLong(slot, amount, doExtract);
    }

    @Override
    public @Nullable IItemStackLong insert(IItemStackLong request, long amount, boolean doInsert) {
        if (amount <= 0) return null;
        request.setStackSize(1);
        int slot = (int) quickAccess.getOrDefault(request, -1);
        IItemStackLong toInsert = request.copy();
        toInsert.setStackSize(amount);
        if (slot >= 0 && request.isItemEqual(handler.getStackInSlotLong(slot))) {
            return handler.insertItemLong(slot, toInsert, doInsert);
        }
        slot = findSlotFromItem(request);
        if (slot < 0) return null;
        quickAccess.put(request, slot);
        return handler.insertItemLong(slot, toInsert, doInsert);
    }

    @Nullable
    protected int findSlotFromItem(IItemStackLong item) {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (item.isItemEqual(handler.getStackInSlotLong(i))) return i;
        }
        return -1;
    }
}
