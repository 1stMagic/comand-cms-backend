package com.biock.cms.component.header;

import com.biock.cms.component.Component;
import com.biock.cms.component.ComponentBuilder;

import javax.validation.constraints.NotNull;

public class HeaderComponent extends Component {

    private final String text;

    public HeaderComponent(
            @NotNull final String name,
            final boolean active,
            @NotNull final String componentName,
            @NotNull final String text) {

        super(name, active, componentName);
        this.text = text;
    }

    public static Builder builder() {

        return new Builder();
    }

    public String getText() {

        return this.text;
    }

    public static final class Builder extends ComponentBuilder<Builder> {

        private String text;

        public Builder text(@NotNull final String text) {

            this.text = text;
            return this;
        }

        public HeaderComponent build() {

            return new HeaderComponent(this.name, this.active, this.componentName, this.text);
        }
    }
}
