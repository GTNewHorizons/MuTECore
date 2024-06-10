package com.blueweabo.mutecore.api.utils;

import com.blueweabo.mutecore.api.enums.TexturePosition;

import net.minecraftforge.common.util.ForgeDirection;

public class DirectionTransformation {

    public static ForgeDirection transformDirection(ForgeDirection direction, TexturePosition position) {
        return switch(position) {
            case BACK -> direction.getOpposite();
            case FRONT -> direction;
            case BOTTOM -> direction.getRotation(ForgeDirection.NORTH);
            case TOP -> direction.getRotation(ForgeDirection.SOUTH);
            case RIGHT -> direction.getRotation(ForgeDirection.DOWN);
            case LEFT -> direction.getRotation(ForgeDirection.UP);
        };
    }
}
