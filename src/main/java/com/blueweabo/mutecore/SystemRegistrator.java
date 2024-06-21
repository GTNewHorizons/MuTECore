package com.blueweabo.mutecore;

import net.minecraft.nbt.NBTTagCompound;

import com.blueweabo.mutecore.api.data.Coordinates;
import com.blueweabo.mutecore.api.data.GUIEvent;
import com.blueweabo.mutecore.api.data.WorldStateValidator;
import com.blueweabo.mutecore.api.gui.MultiTileEntityGuiFactory;
import com.blueweabo.mutecore.api.registry.EventRegistry;
import com.blueweabo.mutecore.api.registry.MultiTileContainer.Id;
import com.blueweabo.mutecore.api.registry.PlayerInteractionEvent;
import com.blueweabo.mutecore.api.utils.PlayerHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dev.dominion.ecs.api.Entity;
import dev.dominion.ecs.api.Results;
import dev.dominion.ecs.api.Scheduler;
import dev.dominion.ecs.engine.IntEntity;

public class SystemRegistrator {

    private static final Scheduler SYSTEMS = MuTECore.ENGINE.createScheduler();

    /**
     * Registers a processing system, which run in parallel with all other processing systems.
     */
    public static void registerProcessingSystem() {
        SYSTEMS.schedule(() -> {});
    }

    /**
     * Registers a GUI system, which will run in parallel with all other GUI systems
     */
    public static void registerGUISystem() {

    }

    /**
     * Registers a system, which will be running after processing systems have ran.
     * Useful if one needs to generate their own system
     */
    public static void registerOtherSystem() {
        EventRegistry.registerPlayerInteractionEvent(
            new PlayerInteractionEvent(0, (p, e) -> PlayerHelper.isRealPlayer(p) ? new GUIEvent(p) : null));
        SYSTEMS.schedule(() -> {
            Results<Results.With1<GUIEvent>> results = MuTECore.ENGINE.findEntitiesWith(GUIEvent.class);
            for (Results.With1<GUIEvent> result : results) {
                Entity entity = result.entity();
                GUIEvent event = entity.get(GUIEvent.class);
                Coordinates coords = entity.get(Coordinates.class);
                Id id = entity.get(Id.class);
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setInteger("entityId", id.getId());
                Object[] components = ((IntEntity) entity).getComponentArray();
                for (Object component : components) {
                    if (component instanceof WorldStateValidator validator) {
                        validator.save(nbt);
                    }
                }
                MultiTileEntityGuiFactory.open(event.getPlayer(), coords.getX(), coords.getY(), coords.getZ(), nbt);
                entity.removeType(GUIEvent.class);
            }
        });
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        SYSTEMS.tick();
    }
}
