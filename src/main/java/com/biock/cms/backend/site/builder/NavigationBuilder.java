package com.biock.cms.backend.site.builder;

import com.biock.cms.backend.site.Navigation;
import com.biock.cms.i18n.Translation;
import com.biock.cms.shared.builder.Builder;

import java.util.List;

public class NavigationBuilder implements Builder<Navigation> {

    private String id;
    private String parentId;
    private Translation topNavigationTitle;
    private Translation mainNavigationTitle;
    private Translation footerNavigationTitle;
    private Translation metaDataTitle;
    private String description;
    private String href;
    private boolean navigationEntry;
    private boolean media;
    private boolean external;
    private boolean active;
    private List<Navigation> children;
    private String jcrPath;

    public NavigationBuilder id(final String id) {

        this.id = id;
        return this;
    }

    public NavigationBuilder parentId(final String parentId) {

        this.parentId = parentId;
        return this;
    }

    public NavigationBuilder topNavigationTitle(final Translation topNavigationTitle) {

        this.topNavigationTitle = topNavigationTitle;
        return this;
    }

    public NavigationBuilder mainNavigationTitle(final Translation mainNavigationTitle) {

        this.mainNavigationTitle = mainNavigationTitle;
        return this;
    }

    public NavigationBuilder footerNavigationTitle(final Translation footerNavigationTitle) {

        this.footerNavigationTitle = footerNavigationTitle;
        return this;
    }

    public NavigationBuilder metaDataTitle(final Translation metaDataTitle) {

        this.metaDataTitle = metaDataTitle;
        return this;
    }

    public NavigationBuilder description(final String description) {

        this.description = description;
        return this;
    }

    public NavigationBuilder href(final String href) {

        this.href = href;
        return this;
    }

    public NavigationBuilder navigationEntry(final boolean navigationEntry) {

        this.navigationEntry = navigationEntry;
        return this;
    }

    public NavigationBuilder media(final boolean media) {

        this.media = media;
        return this;
    }

    public NavigationBuilder external(final boolean external) {

        this.external = external;
        return this;
    }

    public NavigationBuilder active(final boolean active) {

        this.active = active;
        return this;
    }

    public NavigationBuilder children(final List<Navigation> children) {

        this.children = children;
        return this;
    }

    public NavigationBuilder jcrPath(final String jcrPath) {

        this.jcrPath = jcrPath;
        return this;
    }

    @Override
    public NavigationBuilder apply(final Navigation other) {

        if (other != null) {
            return id(other.getId())
                    .parentId(other.getParentId())
                    .topNavigationTitle(other.getTopNavigationTitle())
                    .mainNavigationTitle(other.getMainNavigationTitle())
                    .footerNavigationTitle(other.getFooterNavigationTitle())
                    .metaDataTitle(other.getMetaDataTitle())
                    .description(other.getDescription())
                    .href(other.getHref())
                    .navigationEntry(other.isNavigationEntry())
                    .media(other.isMedia())
                    .external(other.isExternal())
                    .active(other.isActive())
                    .children(other.getChildren())
                    .jcrPath(other.getJcrPath());
        }
        return this;
    }

    @Override
    public Navigation build() {

        return new Navigation(
                this.id,
                this.parentId,
                this.topNavigationTitle,
                this.mainNavigationTitle,
                this.footerNavigationTitle,
                this.metaDataTitle,
                this.description,
                this.href,
                this.navigationEntry,
                this.media,
                this.external,
                this.active,
                this.children,
                this.jcrPath);
    }
}
