package com.blueweabo.mutecore.api.data;

import com.blueweabo.mutecore.api.enums.TexturePosition;

import net.minecraft.util.IIcon;

public class OverlayTextures {

    TexturePosition position;
    IIcon[] textures;

    public OverlayTextures(TexturePosition position, IIcon... textures) {
        this.position = position;
        this.textures = textures;
    }

    public TexturePosition getPosition() {
        return position;
    }

    public void setPosition(TexturePosition position) {
        this.position = position;
    }

    public IIcon[] getTextures() {
        return textures;
    }

    public void setTextures(IIcon[] textures) {
        this.textures = textures;
    }

}
