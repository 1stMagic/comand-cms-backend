package com.biock.cms.component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;

public class ComponentProperty {

    private final String name;
    private final Object value;

    public ComponentProperty(@NotNull final String name, final Object value) {

        this.name = name;
        this.value = value;
    }

    public static Builder builder() {

        return new Builder();
    }

    public String getName() {

        return this.name;
    }

    public Object getValue() {

        return this.value;
    }

    @JsonIgnore
    public <T> T getValueAs(@NotNull final Class<T> c) {

        if (c.isInstance(this.value)) {
            return c.cast(this.value);
        }
        throw new ClassCastException(MessageFormat.format(
                "Instance of class {0} cannot be cast to {1}",
                this.value.getClass().getName(),
                c.getName()));
    }

    public static final class Builder {

        private String name;
        private Object value;

        public Builder name(@NotNull final String name) {

            this.name = name;
            return this;
        }

        public Builder value(final Object value) {

            this.value = value;
            return this;
        }

        public ComponentProperty build() {

            return new ComponentProperty(this.name, this.value);
        }
    }
}
