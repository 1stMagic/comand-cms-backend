package com.biock.cms.component.simple.dto.frontend;

import com.biock.cms.component.Component;
import com.biock.cms.component.dto.ComponentDTO;
import com.biock.cms.component.simple.SimpleComponent;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class SimpleComponentDTO implements ComponentDTO {

    private String componentName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> properties;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SimpleComponentDTO> components;

    public static SimpleComponentDTO of(final SimpleComponent entity, final String language, final String fallbackLanguage) {

        if (entity == null) {
            return new SimpleComponentDTO();
        }
        final Map<String, Object> properties = new HashMap<>();
        Optional.ofNullable(entity.getProperties())
                .orElse(emptyList())
                .forEach(property -> properties.put(property.getName(), property.getValue(language, fallbackLanguage)));
        return new SimpleComponentDTO()
                .setComponentName(entity.getComponentName())
                .setProperties(properties)
                .setComponents(Optional.ofNullable(entity.getComponents())
                        .orElse(emptyList())
                        .stream()
                        .filter(Component::isActive)
                        .map(c -> SimpleComponentDTO.of(c, language, fallbackLanguage))
                        .collect(toList()));
    }

    @Override
    public String getComponentName() {

        return this.componentName;
    }

    public SimpleComponentDTO setComponentName(final String componentName) {

        this.componentName = componentName;
        return this;
    }

    public Map<String, Object> getProperties() {

        return this.properties;
    }

    public SimpleComponentDTO setProperties(final Map<String, Object> properties) {

        this.properties = properties;
        return this;
    }

    public List<SimpleComponentDTO> getComponents() {

        return this.components;
    }

    public SimpleComponentDTO setComponents(final List<SimpleComponentDTO> components) {

        this.components = components;
        return this;
    }
}
