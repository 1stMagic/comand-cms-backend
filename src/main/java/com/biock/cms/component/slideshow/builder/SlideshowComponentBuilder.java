package com.biock.cms.component.slideshow.builder;

import com.biock.cms.component.slideshow.SlideshowComponent;
import com.biock.cms.component.slideshow.SlideshowItem;
import com.biock.cms.shared.EntityId;
import com.biock.cms.shared.builder.Builder;

import java.util.List;

public class SlideshowComponentBuilder implements Builder<SlideshowComponent> {

    private EntityId id;
    private boolean active;
    private String componentName;
    private List<SlideshowItem> items;

    public SlideshowComponentBuilder id(final EntityId id) {

        this.id = id;
        return this;
    }

    public SlideshowComponentBuilder active(final boolean active) {

        this.active = active;
        return this;
    }

    public SlideshowComponentBuilder componentName(final String componentName) {

        this.componentName = componentName;
        return this;
    }

    public SlideshowComponentBuilder items(final List<SlideshowItem> items) {

        this.items = items;
        return this;
    }

    @Override
    public SlideshowComponentBuilder apply(final SlideshowComponent other) {

        if (other != null) {
            final SlideshowComponentBuilder builder = (SlideshowComponentBuilder) id(other.getId())
                    .active(other.isActive())
                    .componentName(other.getComponentName());
            return builder.items(((SlideshowComponent) other).getItems());
        }
        return this;
    }

    @Override
    public SlideshowComponent build() {

        return new SlideshowComponent(
                this.id,
                this.active,
                this.componentName,
                this.items);
    }
}
