package com.gtnewhorizons.mutecore.api.data;

import com.badlogic.ashley.core.Component;

import net.minecraft.entity.player.EntityPlayer;

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
