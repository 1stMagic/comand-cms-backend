package com.biock.cms.component.slideshow;

import com.biock.cms.component.Component;
import com.biock.cms.component.ComponentBuilder;
import com.biock.cms.component.ComponentProperty;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public class SlideshowComponent extends Component {

    private final List<SlideshowItem> items;

    public SlideshowComponent(
            @NotNull final String name,
            final boolean active,
            @NotNull final String componentName,
            @NotNull final List<SlideshowItem> items) {

        super(name, active, componentName, Collections.singletonMap("items", ComponentProperty.builder().name("items").value(items).build()), emptyList());
        this.items = items;
    }

    public static Builder builderx() {

        return new Builder();
    }

    public List<SlideshowItem> getItems() {

        return this.items;
    }

    public static final class Builder extends ComponentBuilder<SlideshowComponent, Builder> {

        private List<SlideshowItem> items;

        public Builder items(@NotNull final List<SlideshowItem> items) {

            this.items = items;
            return this;
        }

        @Override
        public SlideshowComponent build() {

            return new SlideshowComponent(this.name, this.active, this.componentName, this.items);
        }
    }
}
