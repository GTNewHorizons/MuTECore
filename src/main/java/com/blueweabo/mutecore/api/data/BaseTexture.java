package com.blueweabo.mutecore.api.data;

import net.minecraft.util.IIcon;

public class BaseTexture {

    private IIcon texture;

    public BaseTexture(IIcon texture) {
        this.texture = texture;
    }

    public IIcon getTexture() {
        return texture;
    }

    public void setTexture(IIcon texture) {
        this.texture = texture;
    }

}
