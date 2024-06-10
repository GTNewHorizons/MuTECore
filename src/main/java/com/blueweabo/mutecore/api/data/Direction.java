package com.blueweabo.mutecore.api.data;

import net.minecraftforge.common.util.ForgeDirection;

public class Direction {

    private ForgeDirection direction;

    public ForgeDirection getDirection() {
        return direction;
    }

    public void setDirection(ForgeDirection direction) {
        this.direction = direction;
    }

    public Direction(ForgeDirection direction) {
        this.direction = direction;
    }
}
