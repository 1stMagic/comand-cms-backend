package com.biock.cms.component.simple.builder;

import com.biock.cms.component.simple.SimpleComponent;
import com.biock.cms.shared.builder.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class SimpleComponentsBuilder implements Builder<List<SimpleComponent>> {

    private List<SimpleComponent> components;

    public SimpleComponentsBuilder components(final List<SimpleComponent> components) {

        this.components = components;
        return this;
    }

    public SimpleComponentsBuilder component(final SimpleComponent component) {

        if (this.components == null) {
            this.components = new ArrayList<>();
        }
        this.components.add(component);
        return this;
    }

    @Override
    public SimpleComponentsBuilder apply(final List<SimpleComponent> other) {

        if (other != null) {
            return components(other);
        }
        return this;
    }

    @Override
    public List<SimpleComponent> build() {

        return Optional.ofNullable(this.components).orElse(emptyList());
    }
}
