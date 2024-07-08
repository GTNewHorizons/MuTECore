package com.gtnewhorizons.mutecore.api.data;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerUUID implements WorldStateValidator {

    private UUID playerUUID;

    public PlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    @Override
    public void save(NBTTagCompound nbt) {
        NBTTagCompound uuidNBT = new NBTTagCompound();
        uuidNBT.setString("uuid", playerUUID.toString());
        nbt.setTag("playerUUID", uuidNBT);
    }

    @Override
    public void load(NBTTagCompound nbt) {
        NBTTagCompound uuidNBT = nbt.getCompoundTag("playerUUID");
        playerUUID = UUID.fromString(uuidNBT.getString("uuid"));
    }
}
