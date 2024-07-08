package com.gtnewhorizons.mutecore.api.utils;

import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizons.mutecore.api.enums.TexturePosition;

public class DirectionTransformation {

    public static ForgeDirection transformDirection(ForgeDirection direction, TexturePosition position) {
        return switch (position) {
            case BACK -> direction.getOpposite();
            case FRONT -> direction;
            case BOTTOM -> direction.getRotation(ForgeDirection.NORTH);
            case TOP -> direction.getRotation(ForgeDirection.SOUTH);
            case RIGHT -> direction.getRotation(ForgeDirection.DOWN);
            case LEFT -> direction.getRotation(ForgeDirection.UP);
        };
    }
}
