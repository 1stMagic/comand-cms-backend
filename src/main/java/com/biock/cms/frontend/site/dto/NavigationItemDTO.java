package com.biock.cms.frontend.site.dto;

import com.biock.cms.site.NavigationItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class NavigationItemDTO {

    @JsonProperty("name")
    private String title;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String href;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String target;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String iconClass;
    @JsonProperty("subentries")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<NavigationItemDTO> children;

    public static NavigationItemDTO of(final NavigationItem entity, final String language, final String fallbackLanguage) {

        return new NavigationItemDTO()
                .setTitle(entity.getTitle().getTranslation(language, fallbackLanguage))
                .setHref(entity.getHref())
                .setTarget(entity.getTarget())
                .setIconClass(entity.getIconClass())
                .setChildren(Optional.ofNullable(entity.getChildren())
                        .orElse(emptyList())
                        .stream()
                        .map(dto -> NavigationItemDTO.of(dto, language, fallbackLanguage))
                        .collect(toList()));
    }

    public String getTitle() {

        return this.title;
    }

    public NavigationItemDTO setTitle(final String title) {

        this.title = title;
        return this;
    }

    public String getHref() {

        return this.href;
    }

    public NavigationItemDTO setHref(final String href) {

        this.href = href;
        return this;
    }

    public String getTarget() {

        return this.target;
    }

    public NavigationItemDTO setTarget(final String target) {

        this.target = target;
        return this;
    }

    public String getIconClass() {

        return this.iconClass;
    }

    public NavigationItemDTO setIconClass(final String iconClass) {

        this.iconClass = iconClass;
        return this;
    }

    public List<NavigationItemDTO> getChildren() {

        return this.children;
    }

    public NavigationItemDTO setChildren(final List<NavigationItemDTO> children) {

        this.children = children;
        return this;
    }
}
