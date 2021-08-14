package com.biock.cms.component.slideshow;

import com.biock.cms.component.Component;
import com.biock.cms.component.dto.ComponentDTO;
import com.biock.cms.component.slideshow.builder.SlideshowComponentBuilder;
import com.biock.cms.component.slideshow.dto.frontend.SlideshowComponentDTO;
import com.biock.cms.shared.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public class SlideshowComponent extends AbstractEntity<SlideshowComponent> implements Component {

    private final boolean active;
    private final String componentName;
    private final List<SlideshowItem> items;

    public SlideshowComponent(
            final String id,
            final boolean active,
            final String componentName,
            final List<SlideshowItem> items) {

        super(id);
        this.active = active;
        this.componentName = componentName;
        this.items = new ArrayList<>();
        if (items != null) {
            this.items.addAll(items);
        }
    }

    public static SlideshowComponentBuilder builder() {

        return new SlideshowComponentBuilder();
    }

    public boolean isActive() {

        return this.active;
    }

    public String getComponentName() {

        return this.componentName;
    }

    public List<SlideshowItem> getItems() {

        return this.items;
    }

    @Override
    public ComponentDTO toFrontendDTO(final String language, final String fallbackLanguage) {

        return SlideshowComponentDTO.of(this, language, fallbackLanguage);
    }
}
