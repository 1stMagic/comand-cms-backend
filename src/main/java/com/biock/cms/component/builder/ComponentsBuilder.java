package com.biock.cms.component.builder;

import com.biock.cms.component.Component;
import com.biock.cms.shared.builder.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class ComponentsBuilder implements Builder<List<Component>> {

    private List<Component> components;

    public ComponentsBuilder components(final List<Component> components) {

        this.components = components;
        return this;
    }

    public ComponentsBuilder component(final Component component) {

        if (this.components == null) {
            this.components = new ArrayList<>();
        }
        this.components.add(component);
        return this;
    }

    @Override
    public ComponentsBuilder apply(final List<Component> other) {

        return this;
    }

    @Override
    public List<Component> build() {

        return Optional.ofNullable(this.components).orElse(emptyList());
    }
}
