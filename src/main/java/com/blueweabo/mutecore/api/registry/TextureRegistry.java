package com.blueweabo.mutecore.api.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.blueweabo.mutecore.MuTECore;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Internal
public class TextureRegistry {

    private static List<IconContainer> blockIconsToRegister = new ArrayList<>();

    @SubscribeEvent
    public void registerIcon(TextureStitchEvent.Pre event) {
        TextureMap map = event.map;

        if (map.getTextureType() == 0) {
            for (IconContainer blockIcon : blockIconsToRegister) {
                MuTECore.LOG.info("registering icon");
                blockIcon.register(map);
            }
        }
    }

    public static void addBlockIconToRegister(IconContainer icon) {
        blockIconsToRegister.add(icon);
    }
}
