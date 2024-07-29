package com.gtnewhorizons.mutecore.api.utils;

import javax.annotation.Nonnull;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PlayerHelper {

    /**
     * Makes sure that the player is actually real and not a {@link net.minecraftforge.common.util.FakePlayer}.
     * Also checks other implementations that could've been done by not using
     * {@link net.minecraftforge.common.util.FakePlayer}
     */
    public static boolean isRealPlayer(@Nonnull EntityPlayer player) {
        return player instanceof EntityPlayerSP || player instanceof EntityPlayerMP;
    }
}
