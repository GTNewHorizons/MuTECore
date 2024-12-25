package com.gtnewhorizons.mutecore.api.data;

import net.minecraft.entity.player.EntityPlayer;

import com.badlogic.ashley.core.Component;

public class GUIEvent implements Component {

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
