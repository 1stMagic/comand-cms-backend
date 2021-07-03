package com.biock.cms.site;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class SiteNavigationItem {

    @JsonProperty("name")
    private final String label;
    private final String href;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String target;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String iconClass;
    @JsonProperty("subentries")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<SiteNavigationItem> children;

    public SiteNavigationItem(
            @NotNull final String label,
            @NotNull final String href,
            @NotNull final String target,
            @NotNull final String iconClass,
            @NotNull final List<SiteNavigationItem> children) {

        this.label = label;
        this.href = href;
        this.target = target;
        this.iconClass = iconClass;
        this.children = new ArrayList<>(children);
    }

    public String getLabel() {

        return this.label;
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

    public List<SiteNavigationItem> getChildren() {

        return this.children;
    }
}
