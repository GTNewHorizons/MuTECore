package com.gtnewhorizons.mutecore.api.data;

import java.util.UUID;

import com.cleanroommc.modularui.api.IFluidTankLong;
import com.cleanroommc.modularui.utils.fluid.FluidTankLong;
import com.gtnewhorizons.mutecore.api.inventory.FluidInventory;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidOutputInventory implements FluidInventory, WorldStateValidator {

    private IFluidTankLong[] inv;
    private UUID key;

    public FluidOutputInventory(int size, long capacity) {
        inv = new FluidTankLong[size];
        for (int i = 0; i < size; i++) {
            inv[i] = new FluidTankLong(capacity);
        }
        key = UUID.randomUUID();
    }

    @Override
    public IFluidTankLong get(int index) {
        return inv[index];
    }

    @Override
    public void set(int index, IFluidTankLong tank) {
        inv[index] = tank;
    }

    @Override
    public int getSize() {
        return inv.length;
    }

    @Override
    public void save(NBTTagCompound nbt) {
        NBTTagCompound fluidOutInv = new NBTTagCompound();
        fluidOutInv.setString("key", key.toString());
        NBTTagList invNBT = new NBTTagList();
        for (int i = 0; i < getSize(); i++) {
            NBTTagCompound tankNBT = new NBTTagCompound();
            IFluidTankLong tank = get(i);
            tankNBT.setString("fluid", tank.getRealFluid() == null ? "" : tank.getRealFluid().getName());
            tankNBT.setLong("amount", tank.getFluidAmountLong());
            invNBT.appendTag(tankNBT);
        }
        fluidOutInv.setTag("inv", invNBT);
        nbt.setTag("fluidOutInv", fluidOutInv);
    }

    @Override
    public void load(NBTTagCompound nbt) {
        NBTTagCompound fluidOutInv = nbt.getCompoundTag("fluidOutInv");
        key = UUID.fromString(fluidOutInv.getString("key"));
        NBTTagList invNBT = fluidOutInv.getTagList("key", NBT.TAG_LIST);
        for (int i = 0; i < invNBT.tagCount(); i++) {
            NBTTagCompound tankNBT = invNBT.getCompoundTagAt(i);
            IFluidTankLong tank = get(i);
            tank.setFluid(FluidRegistry.getFluid(tankNBT.getString("fluid")), tankNBT.getLong("amount"));
        }
    }
}
