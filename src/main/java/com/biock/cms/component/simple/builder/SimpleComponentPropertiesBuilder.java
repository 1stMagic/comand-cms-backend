package com.biock.cms.component.simple.builder;

import com.biock.cms.component.simple.SimpleComponentProperty;
import com.biock.cms.shared.builder.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class SimpleComponentPropertiesBuilder implements Builder<List<SimpleComponentProperty>> {

    private List<SimpleComponentProperty> properties;

    public SimpleComponentPropertiesBuilder properties(final List<SimpleComponentProperty> properties) {

        this.properties = properties;
        return this;
    }

    public SimpleComponentPropertiesBuilder property(final SimpleComponentProperty property) {

        if (property != null) {
            if (this.properties == null) {
                this.properties = new ArrayList<>();
            }
            this.properties.add(property);
        }
        return this;
    }

    @Override
    public SimpleComponentPropertiesBuilder apply(final List<SimpleComponentProperty> other) {

        if (other != null) {
            return properties(other);
        }
        return this;
    }

    @Override
    public List<SimpleComponentProperty> build() {

        return Optional.ofNullable(this.properties).orElse(emptyList());
    }
}
