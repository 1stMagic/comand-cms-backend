package com.biock.cms.component;

import javax.validation.constraints.NotNull;

@SuppressWarnings("ALL")
public abstract class ComponentBuilder<T extends ComponentBuilder> {

    protected String name;
    protected boolean active;
    protected String componentName;

    public T name(@NotNull final String name) {

        this.name = name;
        return (T) this;
    }

    public T active(final boolean active) {

        this.active = active;
        return (T) this;
    }

    public T componentName(@NotNull final String componentName) {

        this.componentName = componentName;
        return (T) this;
    }
}
