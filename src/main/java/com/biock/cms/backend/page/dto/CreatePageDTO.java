package com.biock.cms.backend.page.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class CreatePageDTO {

    private String parentId;
    @NotEmpty
    private Map<@NotBlank String, @NotNull String> title;
    private boolean showInMainNavigation;
    private boolean showInTopNavigation;
    private boolean showInFooterNavigation;
    private boolean navigationEntry;
    private boolean media;
    private boolean external;
    private String href;
    private String afterPageId;

    public String getParentId() {

        return this.parentId;
    }

    public CreatePageDTO setParentId(final String parentId) {

        this.parentId = parentId;
        return this;
    }

    public Map<String, String> getTitle() {

        return this.title;
    }

    public CreatePageDTO setTitle(final Map<String, String> title) {

        this.title = title;
        return this;
    }

    public boolean isShowInMainNavigation() {

        return this.showInMainNavigation;
    }

    public CreatePageDTO setShowInMainNavigation(final boolean showInMainNavigation) {

        this.showInMainNavigation = showInMainNavigation;
        return this;
    }

    public boolean isShowInTopNavigation() {

        return this.showInTopNavigation;
    }

    public CreatePageDTO setShowInTopNavigation(final boolean showInTopNavigation) {

        this.showInTopNavigation = showInTopNavigation;
        return this;
    }

    public boolean isShowInFooterNavigation() {

        return this.showInFooterNavigation;
    }

    public CreatePageDTO setShowInFooterNavigation(final boolean showInFooterNavigation) {

        this.showInFooterNavigation = showInFooterNavigation;
        return this;
    }

    public boolean isNavigationEntry() {

        return this.navigationEntry;
    }

    public CreatePageDTO setNavigationEntry(final boolean navigationEntry) {

        this.navigationEntry = navigationEntry;
        return this;
    }

    public boolean isMedia() {

        return this.media;
    }

    public CreatePageDTO setMedia(final boolean media) {

        this.media = media;
        return this;
    }

    public boolean isExternal() {

        return this.external;
    }

    public CreatePageDTO setExternal(final boolean external) {

        this.external = external;
        return this;
    }

    public String getHref() {

        return this.href;
    }

    public CreatePageDTO setHref(final String href) {

        this.href = href;
        return this;
    }

    public String getAfterPageId() {

        return this.afterPageId;
    }

    public CreatePageDTO setAfterPageId(final String afterPageId) {

        this.afterPageId = afterPageId;
        return this;
    }
}
