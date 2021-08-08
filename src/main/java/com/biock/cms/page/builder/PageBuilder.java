package com.biock.cms.page.builder;

import com.biock.cms.page.MetaData;
import com.biock.cms.page.Page;
import com.biock.cms.shared.EntityId;
import com.biock.cms.shared.Modification;
import com.biock.cms.i18n.Translation;
import com.biock.cms.shared.builder.Builder;

public class PageBuilder implements Builder<Page> {

    private EntityId id;
    private String description;
    private Modification modification;
    private boolean active;
    private Translation topNavigationTitle;
    private Translation mainNavigationTitle;
    private Translation footerNavigationTitle;
    private boolean showInTopNavigation;
    private boolean showInMainNavigation;
    private boolean showInFooterNavigation;
    private String iconClass;
    private boolean external;
    private String href;
    private String target;
    private MetaData metaData;
    private String jcrPath;

    public PageBuilder id(final EntityId id) {

        this.id = id;
        return this;
    }

    public PageBuilder description(final String description) {

        this.description = description;
        return this;
    }

    public PageBuilder modification(final Modification modification) {

        this.modification = modification;
        return this;
    }

    public PageBuilder active(final boolean active) {

        this.active = active;
        return this;
    }

    public PageBuilder topNavigationTitle(final Translation topNavigationTitle) {

        this.topNavigationTitle = topNavigationTitle;
        return this;
    }

    public PageBuilder mainNavigationTitle(final Translation mainNavigationTitle) {

        this.mainNavigationTitle = mainNavigationTitle;
        return this;
    }

    public PageBuilder footerNavigationTitle(final Translation footerNavigationTitle) {

        this.footerNavigationTitle = footerNavigationTitle;
        return this;
    }

    public PageBuilder showInTopNavigation(final boolean showInTopNavigation) {

        this.showInTopNavigation = showInTopNavigation;
        return this;
    }

    public PageBuilder showInMainNavigation(final boolean showInMainNavigation) {

        this.showInMainNavigation = showInMainNavigation;
        return this;
    }

    public PageBuilder showInFooterNavigation(final boolean showInFooterNavigation) {

        this.showInFooterNavigation = showInFooterNavigation;
        return this;
    }

    public PageBuilder iconClass(final String iconClass) {

        this.iconClass = iconClass;
        return this;
    }

    public PageBuilder external(final boolean external) {

        this.external = external;
        return this;
    }

    public PageBuilder href(final String href) {

        this.href = href;
        return this;
    }

    public PageBuilder target(final String target) {

        this.target = target;
        return this;
    }

    public PageBuilder metaData(final MetaData metaData) {

        this.metaData = metaData;
        return this;
    }

    public PageBuilder jcrPath(final String jcrPath) {

        this.jcrPath = jcrPath;
        return this;
    }

    @Override
    public PageBuilder apply(final Page other) {

        if (other != null) {
            return id(other.getId())
                    .description(other.getDescription())
                    .modification(other.getModification())
                    .active(other.isActive())
                    .topNavigationTitle(other.getTopNavigationTitle())
                    .mainNavigationTitle(other.getMainNavigationTitle())
                    .footerNavigationTitle(other.getFooterNavigationTitle())
                    .showInTopNavigation(other.isShowInTopNavigation())
                    .showInMainNavigation(other.isShowInMainNavigation())
                    .showInFooterNavigation(other.isShowInFooterNavigation())
                    .iconClass(other.getIconClass())
                    .external(other.isExternal())
                    .href(other.getHref())
                    .target(other.getTarget())
                    .metaData(other.getMetaData())
                    .jcrPath(other.getJcrPath());
        }
        return this;
    }

    @Override
    public Page build() {

        return new Page(
                this.id,
                this.description,
                this.modification,
                this.active,
                this.topNavigationTitle,
                this.mainNavigationTitle,
                this.footerNavigationTitle,
                this.showInTopNavigation,
                this.showInMainNavigation,
                this.showInFooterNavigation,
                this.iconClass,
                this.external,
                this.href,
                this.target,
                this.metaData,
                this.jcrPath);
    }
}