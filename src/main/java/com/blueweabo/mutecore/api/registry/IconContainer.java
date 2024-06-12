package com.blueweabo.mutecore.api.registry;

import org.jetbrains.annotations.ApiStatus.Internal;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

@Internal
public class IconContainer {

    private IIcon icon;
    private String path;

    public IconContainer(String path) {
        this.path = path;
    }

    public void register(IIconRegister reg) {
        icon = reg.registerIcon(path);
    }

    public IIcon getIcon() {
        return icon;
    }
}
