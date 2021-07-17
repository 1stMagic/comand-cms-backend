package com.biock.cms.component;

import com.biock.cms.shared.Builder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Component {

    private final String name;
    private final boolean active;
    private final String componentName;
    private final Map<String, ComponentProperty> properties;
    private final List<Component> components;

    public Component(
            @NotNull final String name,
            final boolean active,
            @NotNull final String componentName,
            @NotNull final Map<String, ComponentProperty> properties,
            @NotNull final List<Component> components) {

        this.name = name;
        this.active = active;
        this.componentName = componentName;
        this.properties = properties;
        this.components = components;
    }

    public static Builder builder() {

        return new Builder();
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

    public Map<String, ComponentProperty> getProperties() {

        return this.properties;
    }

    public List<Component> getComponents() {

        return this.components;
    }

    @JsonIgnore
    public boolean isA(final String name) {

        return this.getName().equals(name);
    }

    @JsonIgnore
    public Optional<Component> findFirst(final String name) {

        for (final Component component : this.components) {
            if (component.isA(name)) {
                return Optional.of(component);
            }
            final Optional<Component> childComponent = component.findFirst(name);
            if (childComponent.isPresent()) {
                return childComponent;
            }
        }
        return Optional.empty();
    }

    @JsonIgnore
    public Optional<ComponentProperty> getProperty(final String name) {

        return Optional.ofNullable(this.properties.get(name));
    }

    public static final class Builder implements com.biock.cms.shared.Builder<Component> {

        private String name;
        private boolean active;
        private String componentName;
        private Map<String, ComponentProperty> properties;
        private List<Component> components;

        public Builder name(@NotNull final String name) {

            this.name = name;
            return this;
        }

        public Builder active(final boolean active) {

            this.active = active;
            return this;
        }

        public Builder componentName(@NotNull final String componentName) {

            this.componentName = componentName;
            return this;
        }

        public Builder properties(@NotNull final Map<String, ComponentProperty> properties) {

            this.properties = properties;
            return this;
        }

        public Builder components(@NotNull final List<Component> components) {

            this.components = components;
            return this;
        }

        @Override
        public Component build() {

            return new Component(this.name, this.active, this.componentName, this.properties, this.components);
        }
    }
}
