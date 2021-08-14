package com.biock.cms.backend.site.dto;

import com.biock.cms.backend.site.Navigation;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class NavigationDTO {

    private String id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String parentId;
    private String title;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;
    private String href;
    private boolean external;
    private boolean active;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<NavigationDTO> children;

    public static NavigationDTO of(final Navigation entity, final Function<Navigation, String> titleSupplier) {

        if (entity == null) {
            return new NavigationDTO();
        }
        return new NavigationDTO()
                .setId(entity.getId())
                .setParentId(entity.getParentId() == null ? null : entity.getParentId())
                .setTitle(titleSupplier.apply(entity))
                .setDescription(entity.getDescription())
                .setHref(entity.getHref())
                .setExternal(entity.isExternal())
                .setActive(entity.isActive())
                .setChildren(Optional.ofNullable(entity.getChildren())
                        .orElse(emptyList())
                        .stream()
                        .map(c -> NavigationDTO.of(c, titleSupplier))
                        .collect(toList()));
    }

    public String getId() {

        return this.id;
    }

    public NavigationDTO setId(final String id) {

        this.id = id;
        return this;
    }

    public String getParentId() {

        return this.parentId;
    }

    public NavigationDTO setParentId(final String parentId) {

        this.parentId = parentId;
        return this;
    }

    public String getTitle() {

        return this.title;
    }

    public NavigationDTO setTitle(final String title) {

        this.title = title;
        return this;
    }

    public String getDescription() {

        return this.description;
    }

    public NavigationDTO setDescription(final String description) {

        this.description = description;
        return this;
    }

    public String getHref() {

        return this.href;
    }

    public NavigationDTO setHref(final String href) {

        this.href = href;
        return this;
    }

    public boolean isExternal() {

        return this.external;
    }

    public NavigationDTO setExternal(final boolean external) {

        this.external = external;
        return this;
    }

    public boolean isActive() {

        return this.active;
    }

    public NavigationDTO setActive(final boolean active) {

        this.active = active;
        return this;
    }

    public List<NavigationDTO> getChildren() {

        return this.children;
    }

    public NavigationDTO setChildren(final List<NavigationDTO> children) {

        this.children = children;
        return this;
    }
}
