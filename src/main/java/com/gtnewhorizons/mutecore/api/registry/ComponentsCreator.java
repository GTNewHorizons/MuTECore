package com.gtnewhorizons.mutecore.api.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.badlogic.ashley.core.Component;

public class ComponentsCreator {

    private boolean built;
    private List<Supplier<? extends Component>> componentBuilders = new ArrayList<>();

    public ComponentsCreator() {}

    public ComponentsCreator component(Supplier<? extends Component> componentBuilder) {
        if (built) throw new IllegalStateException("Cannot add components after the builder has been finished");
        componentBuilders.add(componentBuilder);
        return this;
    }

    public ComponentsCreator build() {
        built = true;
        return this;
    }

    public List<Component> createComponents() {
        if (!built) throw new IllegalStateException("Cannot create components when the builder is unfinished");
        List<Component> components = new ArrayList<>();
        for (int i = 0; i < componentBuilders.size(); i++) {
            components.add(componentBuilders.get(i).get());
        }
        return components;
    }
}
