package com.blueweabo.mutecore.api.host;

import com.blueweabo.mutecore.api.logic.interfaces.PowerLogic;

import net.minecraftforge.common.util.ForgeDirection;

public interface PowerLogicHost {

    default PowerLogic getPowerLogic() {
        return getPowerLogic(ForgeDirection.UNKNOWN);
    }

    PowerLogic getPowerLogic(ForgeDirection side);
}
