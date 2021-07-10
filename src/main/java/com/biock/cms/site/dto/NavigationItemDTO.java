package com.biock.cms.site.dto;

import com.biock.cms.site.SiteNavigationItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class NavigationItemDTO {

    private String name;
    private String href;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String target;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String iconClass;
    @JsonProperty("subentries")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<NavigationItemDTO> children;

    public static NavigationItemDTO of(@NotNull final String language, @NotNull final SiteNavigationItem siteNavigationItem) {

        final var dto = new NavigationItemDTO();
        dto.name = siteNavigationItem.getLabel();
        dto.href = siteNavigationItem.getHref();
        dto.target = siteNavigationItem.getTarget();
        dto.iconClass = siteNavigationItem.getIconClass();
        dto.children = siteNavigationItem.getChildren()
                .stream()
                .map(item -> NavigationItemDTO.of(language, item))
                .collect(toList());
        return dto;
    }

    public String getName() {

        return this.name;
    }

    public String getHref() {

        return this.href;
    }

    public String getTarget() {

        return this.target;
    }

    public String getIconClass() {

        return this.iconClass;
    }

    public List<NavigationItemDTO> getChildren() {

        return this.children;
    }
}
