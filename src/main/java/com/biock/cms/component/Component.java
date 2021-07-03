package com.biock.cms.component;

import javax.validation.constraints.NotNull;

public abstract class Component {

    private final String name;
    private final boolean active;
    private final String componentName;

    public Component(@NotNull final String name, final boolean active, @NotNull final String componentName) {

        this.name = name;
        this.active = active;
        this.componentName = componentName;
    }

    public String getName() {

        return this.name;
    }

    public boolean isActive() {

        return this.active;
    }

    public String getComponentName() {

        return this.componentName;
    }
}
