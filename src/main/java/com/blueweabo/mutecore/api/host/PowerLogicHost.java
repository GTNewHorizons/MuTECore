package com.blueweabo.mutecore.api.host;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.blueweabo.mutecore.api.logic.interfaces.PowerLogic;

import net.minecraftforge.common.util.ForgeDirection;

public interface PowerLogicHost {

    default @Nonnull PowerLogic getPowerLogic() {
        return Objects.requireNonNull(getPowerLogic(ForgeDirection.UNKNOWN));
    }

    @Nullable PowerLogic getPowerLogic(ForgeDirection side);
}
