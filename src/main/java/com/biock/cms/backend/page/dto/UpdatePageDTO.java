package com.biock.cms.backend.page.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class UpdatePageDTO {

    private Map<@NotBlank String, @NotNull String> title;
    private Map<@NotBlank String, @NotNull Map<@NotBlank String, @NotNull String>> metaData;
    private Boolean showInMainNavigation;
    private Boolean showInTopNavigation;
    private Boolean showInFooterNavigation;
    private Boolean navigationEntry;
    private Boolean media;
    private Boolean external;
    private String href;
    private Boolean active;

    public Map<String, String> getTitle() {

        return this.title;
    }

    public UpdatePageDTO setTitle(final Map<String, String> title) {

        this.title = title;
        return this;
    }

    public Map<String, Map<String, String>> getMetaData() {

        return this.metaData;
    }

    public UpdatePageDTO setMetaData(final Map<String, Map<String, String>> metaData) {

        this.metaData = metaData;
        return this;
    }

    public Boolean isShowInMainNavigation() {

        return this.showInMainNavigation;
    }

    public UpdatePageDTO setShowInMainNavigation(final Boolean showInMainNavigation) {

        this.showInMainNavigation = showInMainNavigation;
        return this;
    }

    public Boolean isShowInTopNavigation() {

        return this.showInTopNavigation;
    }

    public UpdatePageDTO setShowInTopNavigation(final Boolean showInTopNavigation) {

        this.showInTopNavigation = showInTopNavigation;
        return this;
    }

    public Boolean isShowInFooterNavigation() {

        return this.showInFooterNavigation;
    }

    public UpdatePageDTO setShowInFooterNavigation(final Boolean showInFooterNavigation) {

        this.showInFooterNavigation = showInFooterNavigation;
        return this;
    }

    public Boolean isNavigationEntry() {

        return this.navigationEntry;
    }

    public UpdatePageDTO setNavigationEntry(final Boolean navigationEntry) {

        this.navigationEntry = navigationEntry;
        return this;
    }

    public Boolean isMedia() {

        return this.media;
    }

    public UpdatePageDTO setMedia(final Boolean media) {

        this.media = media;
        return this;
    }

    public Boolean isExternal() {

        return this.external;
    }

    public UpdatePageDTO setExternal(final Boolean external) {

        this.external = external;
        return this;
    }

    public String getHref() {

        return this.href;
    }

    public UpdatePageDTO setHref(final String href) {

        this.href = href;
        return this;
    }

    public Boolean isActive() {

        return this.active;
    }

    public UpdatePageDTO setActive(final Boolean active) {

        this.active = active;
        return this;
    }

    @JsonIgnore
    public boolean isEmpty() {

        return (this.title == null || this.title.isEmpty())
                && this.showInTopNavigation == null
                && this.showInMainNavigation == null
                && this.showInFooterNavigation == null
                && this.navigationEntry == null
                && this.media == null
                && this.external == null
                && this.href == null
                && this.active == null;
    }
}
