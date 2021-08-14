package com.biock.cms.backend.page.dto;

public class ClonePageDTO {

    private String afterPageId;
    private boolean showInMainNavigation;
    private boolean showInTopNavigation;
    private boolean showInFooterNavigation;

    public String getAfterPageId() {

        return this.afterPageId;
    }

    public ClonePageDTO setAfterPageId(final String afterPageId) {

        this.afterPageId = afterPageId;
        return this;
    }

    public boolean isShowInMainNavigation() {

        return this.showInMainNavigation;
    }

    public ClonePageDTO setShowInMainNavigation(final boolean showInMainNavigation) {

        this.showInMainNavigation = showInMainNavigation;
        return this;
    }

    public boolean isShowInTopNavigation() {

        return this.showInTopNavigation;
    }

    public ClonePageDTO setShowInTopNavigation(final boolean showInTopNavigation) {

        this.showInTopNavigation = showInTopNavigation;
        return this;
    }

    public boolean isShowInFooterNavigation() {

        return this.showInFooterNavigation;
    }

    public ClonePageDTO setShowInFooterNavigation(final boolean showInFooterNavigation) {

        this.showInFooterNavigation = showInFooterNavigation;
        return this;
    }
}
