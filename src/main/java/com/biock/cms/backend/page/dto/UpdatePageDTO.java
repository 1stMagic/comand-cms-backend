package com.biock.cms.backend.page.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class UpdatePageDTO {

    private Map<@NotBlank String, @NotNull String> topNavigationTitle;
    private Map<@NotBlank String, @NotNull String> mainNavigationTitle;
    private Map<@NotBlank String, @NotNull String> footerNavigationTitle;
    private Boolean showInMainNavigation;
    private Boolean showInTopNavigation;
    private Boolean showInFooterNavigation;
    private Boolean active;

    public Map<String, String> getTopNavigationTitle() {

        return this.topNavigationTitle;
    }

    public UpdatePageDTO setTopNavigationTitle(final Map<String, String> topNavigationTitle) {

        this.topNavigationTitle = topNavigationTitle;
        return this;
    }

    public Map<String, String> getMainNavigationTitle() {

        return this.mainNavigationTitle;
    }

    public UpdatePageDTO setMainNavigationTitle(final Map<String, String> mainNavigationTitle) {

        this.mainNavigationTitle = mainNavigationTitle;
        return this;
    }

    public Map<String, String> getFooterNavigationTitle() {

        return this.footerNavigationTitle;
    }

    public UpdatePageDTO setFooterNavigationTitle(final Map<String, String> footerNavigationTitle) {

        this.footerNavigationTitle = footerNavigationTitle;
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

    public Boolean isActive() {

        return this.active;
    }

    public UpdatePageDTO setActive(final Boolean active) {

        this.active = active;
        return this;
    }

    @JsonIgnore
    public boolean isEmpty() {

        return (this.topNavigationTitle == null || this.topNavigationTitle.isEmpty())
                && (this.mainNavigationTitle == null || this.mainNavigationTitle.isEmpty())
                && (this.footerNavigationTitle == null || this.footerNavigationTitle.isEmpty())
                && this.showInTopNavigation == null
                && this.showInMainNavigation == null
                && this.showInFooterNavigation == null
                && this.active == null;
    }
}
