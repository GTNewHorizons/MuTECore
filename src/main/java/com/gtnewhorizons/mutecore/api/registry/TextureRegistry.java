package com.gtnewhorizons.mutecore.api.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.gtnewhorizons.mutecore.MuTECore;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Internal
public class TextureRegistry {

    private static List<Object> blockIconsToRegister = new ArrayList<>();

    @SubscribeEvent
    public void registerIcon(TextureStitchEvent.Pre event) {
        TextureMap map = event.map;

        if (map.getTextureType() == 0) {
            for (Object blockIcon : blockIconsToRegister) {
                MuTECore.LOG.info("registering icon");
            }
        }
    }

    public static void registerBlockIcon(Object icon) {
        blockIconsToRegister.add(icon);
    }
}
