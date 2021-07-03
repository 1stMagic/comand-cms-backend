package com.biock.cms.component.wrapper;

import com.biock.cms.component.Component;
import com.biock.cms.component.ComponentBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

public class WidthLimitationWrapperComponent extends Component {

    private final List<Component> components;

    public WidthLimitationWrapperComponent(
            @NotNull final String name,
            final boolean active,
            @NotNull final String componentName,
            @NotNull final List<Component> components) {

        super(name, active, componentName);
        this.components = components;
    }

    public static Builder builder() {

        return new Builder();
    }

    public List<Component> getComponents() {

        return this.components;
    }

    public static final class Builder extends ComponentBuilder<Builder> {

        private List<Component> components;

        public Builder components(@NotNull final List<Component> components) {

            this.components = components;
            return this;
        }

        public WidthLimitationWrapperComponent build() {

            return new WidthLimitationWrapperComponent(this.name, this.active, this.componentName, this.components);
        }
    }
}
