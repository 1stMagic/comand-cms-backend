package com.biock.cms.component.simple.builder;

import com.biock.cms.component.simple.SimpleComponent;
import com.biock.cms.component.simple.SimpleComponentProperty;
import com.biock.cms.shared.builder.Builder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SimpleComponentBuilder implements Builder<SimpleComponent> {

    private String id;
    private boolean active;
    private String componentName;
    private List<SimpleComponentProperty> properties;
    private List<SimpleComponent> components;

    public SimpleComponentBuilder id(final String id) {

        this.id = id;
        return this;
    }

    public SimpleComponentBuilder active(final boolean active) {

        this.active = active;
        return this;
    }

    public SimpleComponentBuilder componentName(final String componentName) {

        this.componentName = componentName;
        return this;
    }

    public SimpleComponentBuilder properties(final List<SimpleComponentProperty> properties) {

        this.properties = properties;
        return this;
    }

    public SimpleComponentBuilder property(final SimpleComponentProperty property) {

        if (this.properties == null) {
            this.properties = new ArrayList<>();
        }
        this.properties.add(property);
        return this;
    }

    public SimpleComponentBuilder components(final List<SimpleComponent> components) {

        this.components = components;
        return this;
    }

    public SimpleComponentBuilder component(final SimpleComponent component) {

        if (this.components == null) {
            this.components = new ArrayList<>();
        }
        this.components.add(component);
        return this;
    }

    @Override
    public SimpleComponentBuilder apply(final SimpleComponent other) {

        if (other != null) {
            return id(other.getId())
                    .active(other.isActive())
                    .componentName(other.getComponentName())
                    .properties(other.getProperties())
                    .components(other.getComponents());
        }
        return this;
    }

    @Override
    public SimpleComponent build() {

        return new SimpleComponent(
                StringUtils.defaultIfBlank(this.id, UUID.randomUUID().toString()),
                this.active,
                this.componentName,
                this.properties,
                this.components
        );
    }
}
