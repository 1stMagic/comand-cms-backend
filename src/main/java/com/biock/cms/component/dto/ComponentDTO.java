package com.biock.cms.component.dto;

import com.biock.cms.component.Component;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class ComponentDTO {

    private String name;
    private String componentName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, ComponentPropertyDTO> properties;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ComponentDTO> components;

    public static ComponentDTO of(@NotNull final String language, @NotNull final Component component) {

        final var dto = new ComponentDTO();
        dto.name = component.getName();
        dto.componentName = component.getComponentName();
        dto.properties = component.getProperties()
                .entrySet()
                .stream()
                .map(entry -> new SimpleEntry<>(entry.getKey(), ComponentPropertyDTO.of(language, entry.getValue())))
                .collect(toMap(Entry::getKey, Entry::getValue));
        dto.components = component.getComponents().stream().map(c -> ComponentDTO.of(language, c)).collect(toList());
        return dto;
    }

    public String getName() {

        return this.name;
    }

    public String getComponentName() {

        return this.componentName;
    }

    public Map<String, ComponentPropertyDTO> getProperties() {

        return this.properties;
    }

    public List<ComponentDTO> getComponents() {

        return this.components;
    }
}
