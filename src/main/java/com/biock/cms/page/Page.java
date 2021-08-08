package com.biock.cms.page;

import com.biock.cms.page.builder.PageBuilder;
import com.biock.cms.shared.AbstractEntity;
import com.biock.cms.shared.EntityId;
import com.biock.cms.shared.Modification;
import com.biock.cms.i18n.Translation;

public class Page extends AbstractEntity<Page> {

    private final String description;
    private final Modification modification;
    private final boolean active;
    private final Translation topNavigationTitle;
    private final Translation mainNavigationTitle;
    private final Translation footerNavigationTitle;
    private final boolean showInTopNavigation;
    private final boolean showInMainNavigation;
    private final boolean showInFooterNavigation;
    private final String iconClass;
    private final boolean external;
    private final String href;
    private final String target;
    private final MetaData metaData;
    private final String jcrPath;

    public Page(
            final EntityId id,
            final String description,
            final Modification modification,
            final boolean active,
            final Translation topNavigationTitle,
            final Translation mainNavigationTitle,
            final Translation footerNavigationTitle,
            final boolean showInTopNavigation,
            final boolean showInMainNavigation,
            final boolean showInFooterNavigation,
            final String iconClass,
            final boolean external,
            final String href,
            final String target,
            final MetaData metaData,
            final String jcrPath) {

        super(id);
        this.description = description;
        this.modification = modification;
        this.active = active;
        this.topNavigationTitle = topNavigationTitle;
        this.mainNavigationTitle = mainNavigationTitle;
        this.footerNavigationTitle = footerNavigationTitle;
        this.showInTopNavigation = showInTopNavigation;
        this.showInMainNavigation = showInMainNavigation;
        this.showInFooterNavigation = showInFooterNavigation;
        this.iconClass = iconClass;
        this.external = external;
        this.href = href;
        this.target = target;
        this.metaData = metaData;
        this.jcrPath = jcrPath;
    }

    public static PageBuilder builder() {

        return new PageBuilder();
    }

    public String getDescription() {

        return this.description;
    }

    public Modification getModification() {

        return this.modification;
    }

    public boolean isActive() {

        return this.active;
    }

    public Translation getTopNavigationTitle() {

        return this.topNavigationTitle;
    }

    public Translation getMainNavigationTitle() {

        return this.mainNavigationTitle;
    }

    public Translation getFooterNavigationTitle() {

        return this.footerNavigationTitle;
    }

    public boolean isShowInTopNavigation() {

        return this.showInTopNavigation;
    }

    public boolean isShowInMainNavigation() {

        return this.showInMainNavigation;
    }

    public boolean isShowInFooterNavigation() {

        return this.showInFooterNavigation;
    }

    public String getIconClass() {

        return this.iconClass;
    }

    public boolean isExternal() {

        return this.external;
    }

    public String getHref() {

        return this.href;
    }

    public String getTarget() {

        return this.target;
    }

    public MetaData getMetaData() {

        return this.metaData;
    }

    public String getJcrPath() {

        return this.jcrPath;
    }
}
