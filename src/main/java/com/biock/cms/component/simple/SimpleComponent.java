package com.biock.cms.component.simple;

import com.biock.cms.component.Component;
import com.biock.cms.component.dto.ComponentDTO;
import com.biock.cms.component.simple.builder.SimpleComponentBuilder;
import com.biock.cms.component.simple.dto.frontend.SimpleComponentDTO;
import com.biock.cms.shared.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public class SimpleComponent extends AbstractEntity<SimpleComponent> implements Component {

    private final boolean active;
    private final String componentName;
    private final List<SimpleComponentProperty> properties;
    private final List<SimpleComponent> components;

    public SimpleComponent(
            final String id,
            final boolean active,
            final String componentName,
            final List<SimpleComponentProperty> properties,
            final List<SimpleComponent> components) {

        super(id);
        this.active = active;
        this.componentName = componentName;
        this.properties = new ArrayList<>();
        this.components = new ArrayList<>();

        if (properties != null) {
            this.properties.addAll(properties);
        }
        if (components != null) {
            this.components.addAll(components);
        }
    }

    public static SimpleComponentBuilder builder() {

        return new SimpleComponentBuilder();
    }

    @Override
    public boolean isActive() {

        return this.active;
    }

    @Override
    public String getComponentName() {

        return this.componentName;
    }

    public List<SimpleComponentProperty> getProperties() {

        return new ArrayList<>(this.properties);
    }

    public List<SimpleComponent> getComponents() {

        return new ArrayList<>(this.components);
    }

    @Override
    public ComponentDTO toFrontendDTO(final String language, final String fallbackLanguage) {

        return SimpleComponentDTO.of(this, language, fallbackLanguage);
    }
}
