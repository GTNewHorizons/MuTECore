package com.blueweabo.mutecore.api.data;

import net.minecraft.entity.player.EntityPlayer;

public class GUIEvent {

    private EntityPlayer player;

    public GUIEvent(EntityPlayer player) {
        this.player = player;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

}
