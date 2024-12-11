package com.gtnewhorizons.mutecore.api.gui;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.gtnewhorizons.mutecore.MuTECore;
import com.gtnewhorizons.mutecore.api.data.Coordinates;
import com.gtnewhorizons.mutecore.api.data.GUIEvent;
import com.gtnewhorizons.mutecore.api.data.WorldStateValidator;

import net.minecraft.nbt.NBTTagCompound;

public class GUISystem extends EntitySystem {

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> results = MuTECore.ENGINE.getEntitiesFor(Family.one(GUIEvent.class).get());
        for (Entity entity : results) {
            GUIEvent event = entity.getComponent(GUIEvent.class);
            Coordinates coords = entity.getComponent(Coordinates.class);
            NBTTagCompound nbt = new NBTTagCompound();
            ImmutableArray<Component> components = entity.getComponents();
            for (Object component : components) {
                if (component instanceof WorldStateValidator validator) {
                    validator.save(nbt);
                }
            }
            MultiTileEntityGuiFactory.open(event.getPlayer(), coords.getX(), coords.getY(), coords.getZ(), nbt);
            entity.remove(GUIEvent.class);
        }
    }
}
