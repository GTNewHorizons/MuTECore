package com.blueweabo.mutecore.api.utils;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderHelper {

    public static void render(IIcon icon, int x, int y, int z, ForgeDirection direction) {
        float minU = icon.getMinU();
        float maxU = icon.getMaxU();
        float minV = icon.getMinV();
        float maxV = icon.getMaxV();
        Tessellator.instance.addVertexWithUV(x + 1 * direction.offsetX, y + 1 * direction.offsetY, z + 1 * direction.offsetZ, minU, minV);
        Tessellator.instance.addVertexWithUV(x + 1 * direction.offsetX, y + 1 * direction.offsetY, z + 1 * direction.offsetZ, maxU, minV);
        Tessellator.instance.addVertexWithUV(x + 1 * direction.offsetX, y + 1 * direction.offsetY, z + 1 * direction.offsetZ, maxU, maxV);
        Tessellator.instance.addVertexWithUV(x + 1 * direction.offsetX, y + 1 * direction.offsetY, z + 1 * direction.offsetZ, minU, maxV);
    }
}
