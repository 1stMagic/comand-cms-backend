package com.biock.cms.site;

import com.biock.cms.i18n.Translation;
import com.biock.cms.shared.ValueObject;
import com.biock.cms.site.builder.NavigationItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class NavigationItem implements ValueObject<NavigationItem> {

    private final Translation title;
    private final String href;
    private final String target;
    private final String iconClass;
    private final List<NavigationItem> children;

    public NavigationItem(
            final Translation title,
            final String href,
            final String target,
            final String iconClass,
            final List<NavigationItem> children) {

        this.title = title;
        this.href = href;
        this.target = target;
        this.iconClass = iconClass;
        this.children = new ArrayList<>();
        if (children != null) {
            this.children.addAll(children);
        }
    }

    public static NavigationItemBuilder builder() {

        return new NavigationItemBuilder();
    }

    public Translation getTitle() {

        return this.title;
    }

    public String getHref() {

        return this.href;
    }

    public String getTarget() {

        return this.target;
    }

    public String getIconClass() {

        return this.iconClass;
    }

    public List<NavigationItem> getChildren() {

        return new ArrayList<>(this.children);
    }

    @Override
    public int compareTo(final NavigationItem other) {

        int c = this.title.compareTo(other.getTitle());
        if (c != 0) {
            return c;
        }
        c = this.href.compareTo(other.getHref());
        if (c != 0) {
            return c;
        }
        c = this.target.compareTo(other.getTarget());
        if (c != 0) {
            return c;
        }
        return this.iconClass.compareTo(other.getIconClass());
    }
}
