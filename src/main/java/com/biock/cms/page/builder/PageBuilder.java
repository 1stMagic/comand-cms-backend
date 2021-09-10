package com.biock.cms.page.builder;

import com.biock.cms.component.Component;
import com.biock.cms.page.MetaData;
import com.biock.cms.page.Page;
import com.biock.cms.shared.Modification;
import com.biock.cms.i18n.Translation;
import com.biock.cms.shared.builder.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PageBuilder implements Builder<Page> {

    private String id;
    private String name;
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
    private boolean navigationEntry;
    private boolean media;
    private boolean external;
    private String href;
    private String target;
    private String[] requiredGroups;
    private MetaData metaData;
    private String jcrPath;
    private List<Component> components;

    public PageBuilder id(final String id) {

        this.id = id;
        return this;
    }

    public PageBuilder name(final String name) {

        this.name = name;
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

    public PageBuilder navigationEntry(final boolean navigationEntry) {

        this.navigationEntry = navigationEntry;
        return this;
    }

    public PageBuilder media(final boolean media) {

        this.media = media;
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

    public PageBuilder requiredGroups(final String[] requiredGroups) {

        this.requiredGroups = requiredGroups;
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

    public PageBuilder components(final List<Component> components) {

        this.components = components;
        return this;
    }

    public PageBuilder component(final Component component) {

        if (this.components == null) {
            this.components = new ArrayList<>();
        }
        this.components.add(component);
        return this;
    }

    @Override
    public PageBuilder apply(final Page other) {

        if (other != null) {
            return id(other.getId())
                    .name(other.getName())
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
                    .navigationEntry(other.isNavigationEntry())
                    .media(other.isMedia())
                    .external(other.isExternal())
                    .href(other.getHref())
                    .target(other.getTarget())
                    .requiredGroups(other.getRequiredGroups())
                    .metaData(other.getMetaData())
                    .jcrPath(other.getJcrPath())
                    .components(other.getComponents());
        }
        return this;
    }

    @Override
    public Page build() {

        return new Page(
                this.id,
                this.name,
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
                this.navigationEntry,
                this.media,
                this.external,
                this.href,
                this.target,
                Optional.ofNullable(this.requiredGroups).orElse(new String[0]),
                this.metaData,
                this.jcrPath,
                this.components);
    }
}
