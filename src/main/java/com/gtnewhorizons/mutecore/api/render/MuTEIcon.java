package com.gtnewhorizons.mutecore.api.render;

import com.gtnewhorizons.mutecore.api.registry.TextureRegistry;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class MuTEIcon implements IIcon {

    private String iconPath;
    private IIcon icon;


    public MuTEIcon(String modid, String path) {
        this(new ResourceLocation(modid, path).toString());
    }

    public MuTEIcon(String path) {
        iconPath = path;
        TextureRegistry.registerBlockIcon(this);
    }

    public void register(IIconRegister reg) {
        icon = reg.registerIcon(iconPath);
    }

    @Override
    public int getIconWidth() {
        return icon.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return icon.getIconHeight();
    }

    @Override
    public float getMinU() {
        return icon.getMinU();
    }

    @Override
    public float getMaxU() {
        return icon.getMaxU();
    }

    @Override
    public float getInterpolatedU(double p_94214_1_) {
        return icon.getInterpolatedU(p_94214_1_);
    }

    @Override
    public float getMinV() {
        return icon.getMinV();
    }

    @Override
    public float getMaxV() {
        return icon.getMaxV();
    }

    @Override
    public float getInterpolatedV(double p_94207_1_) {
        return icon.getInterpolatedV(p_94207_1_);
    }

    @Override
    public String getIconName() {
        return icon.getIconName();
    }

}
