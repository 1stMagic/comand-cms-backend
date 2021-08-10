package com.biock.cms.component.slideshow.dto.frontend;

import com.biock.cms.component.dto.ComponentDTO;
import com.biock.cms.component.slideshow.SlideshowComponent;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;

public class SlideshowComponentDTO implements ComponentDTO {

    private String componentName;
    private Map<String, List<SlideshowItemDTO>> properties;

    public static SlideshowComponentDTO of(final SlideshowComponent entity, final String language, final String fallbackLanguage) {

        if (entity == null) {
            return new SlideshowComponentDTO();
        }
        return new SlideshowComponentDTO()
                .setComponentName(entity.getComponentName())
                .setProperties(singletonMap(
                        "items",
                        Optional.ofNullable(entity.getItems())
                                .orElse(emptyList())
                                .stream()
                                .map(SlideshowItemDTO::of)
                                .collect(toList())));
    }

    @Override
    public String getComponentName() {

        return this.componentName;
    }

    public SlideshowComponentDTO setComponentName(final String componentName) {

        this.componentName = componentName;
        return this;
    }

    public Map<String, List<SlideshowItemDTO>> getProperties() {

        return this.properties;
    }

    public SlideshowComponentDTO setProperties(final Map<String, List<SlideshowItemDTO>> properties) {

        this.properties = properties;
        return this;
    }
}
