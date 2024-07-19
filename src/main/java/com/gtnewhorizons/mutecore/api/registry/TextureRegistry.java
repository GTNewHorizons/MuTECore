package com.gtnewhorizons.mutecore.api.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.gtnewhorizons.mutecore.MuTECore;
import com.gtnewhorizons.mutecore.api.render.MuTEIcon;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Internal
public class TextureRegistry {

    private static List<MuTEIcon> blockIconsToRegister = new ArrayList<>();

    @SubscribeEvent
    public void registerIcon(TextureStitchEvent.Pre event) {
        TextureMap map = event.map;

        if (map.getTextureType() == 0) {
            for (MuTEIcon blockIcon : blockIconsToRegister) {
                MuTECore.LOG.info("registering icon");
                blockIcon.register(map);
            }
        }
    }

    public static void registerBlockIcon(MuTEIcon icon) {
        blockIconsToRegister.add(icon);
    }
}
