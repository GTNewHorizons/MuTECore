package com.blueweabo.mutecore.api.logic;

import javax.annotation.ParametersAreNonnullByDefault;

import com.blueweabo.mutecore.api.logic.interfaces.PowerLogic;
import com.cleanroommc.modularui.api.IItemStackLong;

import net.minecraft.tileentity.TileEntityFurnace;

@ParametersAreNonnullByDefault
public class FuelPowerLogic implements PowerLogic {

    private ItemInventoryLogic container;
    private long burnTime;

    @Override
    public boolean consume(long tick) {
        if (burnTime > 0) {
            burnTime--;
            return true;
        }
        IItemStackLong foundFuel = null;
        for (int i = 0; i < container.getSlots(); i++) {
            if (container.get(i) != null && TileEntityFurnace.isItemFuel(container.get(i).getAsItemStack())) {
                foundFuel = container.get(i);
                break;
            }
        }
        if (foundFuel == null) {
            return false;
        }
        IItemStackLong extractTry = container.extract(foundFuel, 1, false);
        if (extractTry == null || extractTry.getStackSize() < 1) {
            return false;
        }
        container.extract(foundFuel, 1, true);
        burnTime = TileEntityFurnace.getItemBurnTime(foundFuel.getAsItemStack());
        return true;
    }

    @Override
    public boolean generate(long tick) {
        return true;
    }

}
