package com.biock.cms.component;

import com.biock.cms.shared.Builder;

import javax.validation.constraints.NotNull;

@SuppressWarnings("ALL")
public abstract class ComponentBuilder<E, T extends ComponentBuilder> implements Builder<E> {

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
