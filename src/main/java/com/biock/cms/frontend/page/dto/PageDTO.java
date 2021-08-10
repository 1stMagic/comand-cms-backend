package com.biock.cms.frontend.page.dto;

import com.biock.cms.component.Component;
import com.biock.cms.component.dto.ComponentDTO;
import com.biock.cms.page.MetaData;
import com.biock.cms.page.Page;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class PageDTO {

    private String id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> metaData;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ComponentDTO> components;

    public static PageDTO of(final Page entity, final String language, final String fallbackLanguage) {

        if (entity == null) {
            return new PageDTO();
        }
        final Map<String, String> metaData = new HashMap<>();
        Optional.ofNullable(entity.getMetaData())
                .orElse(MetaData.empty())
                .getMetaData()
                .forEach((k, v) -> metaData.put(k, v.getTranslation(language, fallbackLanguage)));
        return new PageDTO()
                .setId(entity.getId().getId())
                .setName(entity.getName())
                .setMetaData(metaData)
                .setComponents(Optional.ofNullable(entity.getComponents())
                        .orElse(emptyList())
                        .stream()
                        .filter(Component::isActive)
                        .map(c -> c.toFrontendDTO(language, fallbackLanguage))
                        .collect(toList()));
    }

    public String getId() {

        return this.id;
    }

    public PageDTO setId(final String id) {

        this.id = id;
        return this;
    }

    public String getName() {

        return this.name;
    }

    public PageDTO setName(final String name) {

        this.name = name;
        return this;
    }

    public Map<String, String> getMetaData() {

        return this.metaData;
    }

    public PageDTO setMetaData(final Map<String, String> metaData) {

        this.metaData = metaData;
        return this;
    }

    public List<ComponentDTO> getComponents() {

        return this.components;
    }

    public PageDTO setComponents(final List<ComponentDTO> components) {

        this.components = components;
        return this;
    }
}
