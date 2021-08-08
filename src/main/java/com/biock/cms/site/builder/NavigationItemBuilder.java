package com.biock.cms.site.builder;

import com.biock.cms.i18n.Translation;
import com.biock.cms.shared.builder.Builder;
import com.biock.cms.site.NavigationItem;

import java.util.List;

public class NavigationItemBuilder implements Builder<NavigationItem> {

    private Translation title;
    private String href;
    private String target;
    private String iconClass;
    private List<NavigationItem> children;

    public NavigationItemBuilder title(final Translation title) {

        this.title = title;
        return this;
    }

    public NavigationItemBuilder href(final String href) {

        this.href = href;
        return this;
    }

    public NavigationItemBuilder target(final String target) {

        this.target = target;
        return this;
    }

    public NavigationItemBuilder iconClass(final String iconClass) {

        this.iconClass = iconClass;
        return this;
    }

    public NavigationItemBuilder children(final List<NavigationItem> children) {

        this.children = children;
        return this;
    }

    @Override
    public NavigationItemBuilder apply(final NavigationItem other) {

        if (other != null) {
            return title(other.getTitle())
                    .href(other.getHref())
                    .target(other.getTarget())
                    .iconClass(other.getIconClass())
                    .children(other.getChildren());
        }
        return this;
    }

    @Override
    public NavigationItem build() {

        return new NavigationItem(
                this.title,
                this.href,
                this.target,
                this.iconClass,
                this.children);
    }
}
