package com.biock.cms.admin.page.dto;

import com.biock.cms.shared.dto.DescriptorDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class CreatePageDTO {

    private String parentId;
    private boolean active;
    @NotEmpty
    private Map<@NotBlank String, @NotNull String> title;
    private Map<String, String> mainNavigationTitle;
    private Map<String, String> topNavigationTitle;
    private Map<String, String> footerNavigationTitle;
    @Valid
    private DescriptorDTO descriptor;
    private Map<String, String> metaData;
    private boolean showInMainNavigation;
    private boolean showInTopNavigation;
    private boolean showInFooterNavigation;
    private boolean external;
    private String href;
    private String target;
    private String iconClass;
    private String beforePageId;

    public String getParentId() {

        return this.parentId;
    }

    public CreatePageDTO setParentId(final String parentId) {

        this.parentId = parentId;
        return this;
    }

    public boolean isActive() {

        return this.active;
    }

    public CreatePageDTO setActive(final boolean active) {

        this.active = active;
        return this;
    }

    public Map<String, String> getTitle() {

        return this.title;
    }

    public CreatePageDTO setTitle(final Map<String, String> title) {

        this.title = title;
        return this;
    }

    public Map<String, String> getMainNavigationTitle() {

        return this.mainNavigationTitle;
    }

    public CreatePageDTO setMainNavigationTitle(final Map<String, String> mainNavigationTitle) {

        this.mainNavigationTitle = mainNavigationTitle;
        return this;
    }

    public Map<String, String> getTopNavigationTitle() {

        return this.topNavigationTitle;
    }

    public CreatePageDTO setTopNavigationTitle(final Map<String, String> topNavigationTitle) {

        this.topNavigationTitle = topNavigationTitle;
        return this;
    }

    public Map<String, String> getFooterNavigationTitle() {

        return this.footerNavigationTitle;
    }

    public CreatePageDTO setFooterNavigationTitle(final Map<String, String> footerNavigationTitle) {

        this.footerNavigationTitle = footerNavigationTitle;
        return this;
    }

    public DescriptorDTO getDescriptor() {

        return this.descriptor;
    }

    public CreatePageDTO setDescriptor(final DescriptorDTO descriptor) {

        this.descriptor = descriptor;
        return this;
    }

    public Map<String, String> getMetaData() {

        return this.metaData;
    }

    public CreatePageDTO setMetaData(final Map<String, String> metaData) {

        this.metaData = metaData;
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

    public String getTarget() {

        return this.target;
    }

    public CreatePageDTO setTarget(final String target) {

        this.target = target;
        return this;
    }

    public String getIconClass() {

        return this.iconClass;
    }

    public CreatePageDTO setIconClass(final String iconClass) {

        this.iconClass = iconClass;
        return this;
    }

    public String getBeforePageId() {

        return this.beforePageId;
    }

    public CreatePageDTO setBeforePageId(final String beforePageId) {

        this.beforePageId = beforePageId;
        return this;
    }

    @Override
    public String toString() {

        return "AdminPageDTO{" +
                "parentId='" + parentId + '\'' +
                ", active=" + active +
                ", title=" + title +
                ", mainNavigationTitle=" + mainNavigationTitle +
                ", topNavigationTitle=" + topNavigationTitle +
                ", footerNavigationTitle=" + footerNavigationTitle +
                ", descriptor=" + descriptor +
                ", metaData=" + metaData +
                ", showInMainNavigation=" + showInMainNavigation +
                ", showInTopNavigation=" + showInTopNavigation +
                ", showInFooterNavigation=" + showInFooterNavigation +
                ", external=" + external +
                ", href='" + href + '\'' +
                ", target='" + target + '\'' +
                ", iconClass='" + iconClass + '\'' +
                ", beforePageId='" + beforePageId + '\'' +
                '}';
    }
}
