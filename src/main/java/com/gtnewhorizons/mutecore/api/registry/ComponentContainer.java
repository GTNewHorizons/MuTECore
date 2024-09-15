package com.gtnewhorizons.mutecore.api.registry;

import java.lang.reflect.Constructor;

import com.gtnewhorizons.mutecore.MuTECore;

public class ComponentContainer {

    private final Class<?> clazz;
    private final Object[] constructorArgs;

    public ComponentContainer(Class<?> clazz, Object... constructorArgs) {
        this.clazz = clazz;
        this.constructorArgs = constructorArgs;
    }

    public Object create() {
        try {
            Class<?>[] argTypes = new Class<?>[constructorArgs.length];
            for (int i = 0; i < constructorArgs.length; i++) {
                argTypes[i] = constructorArgs[i].getClass();
            }
            Constructor<?> constructor = clazz.getDeclaredConstructor(argTypes);
            return constructor.newInstance(constructorArgs);
        } catch (Exception e) {
            MuTECore.LOG.error(e.getMessage());
            return null;
        }
    }
}
