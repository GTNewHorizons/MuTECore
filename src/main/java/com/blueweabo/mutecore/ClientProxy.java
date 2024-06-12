package com.blueweabo.mutecore;

import com.blueweabo.mutecore.api.registry.TextureRegistry;
import com.blueweabo.mutecore.client.MultiTileBlockRenderer;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        new MultiTileBlockRenderer();
        MinecraftForge.EVENT_BUS.register(new TextureRegistry());
    }
}
