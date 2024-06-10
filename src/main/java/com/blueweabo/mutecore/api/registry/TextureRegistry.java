package com.blueweabo.mutecore.api.registry;

import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;

public class TextureRegistry {

    private static List<?> thing;

    @SubscribeEvent
    public void registerIcon(TextureStitchEvent.Pre event) {
        TextureMap map = event.map;

        if (map.getTextureType() == 0) {

        }
    }
}
