package com.biock.cms.backend.site;

import com.biock.cms.backend.site.builder.NavigationBuilder;
import com.biock.cms.i18n.Translation;
import com.biock.cms.shared.AbstractEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class Navigation extends AbstractEntity<Navigation> { // NOSONAR -java:S2160

    private final String parentId;
    private final Translation topNavigationTitle;
    private final Translation mainNavigationTitle;
    private final Translation footerNavigationTitle;
    private final Translation metaDataTitle;
    private final String description;
    private final String href;
    private final boolean navigationEntry;
    private final boolean media;
    private final boolean external;
    private final boolean active;
    private final List<Navigation> children;
    private final String jcrPath;

    public Navigation(
            final String id,
            final String parentId,
            final Translation topNavigationTitle,
            final Translation mainNavigationTitle,
            final Translation footerNavigationTitle,
            final Translation metaDataTitle,
            final String description,
            final String href,
            final boolean navigationEntry,
            final boolean media,
            final boolean external,
            final boolean active,
            final List<Navigation> children,
            final String jcrPath) {

        super(id);
        this.parentId = parentId;
        this.topNavigationTitle = topNavigationTitle;
        this.mainNavigationTitle = mainNavigationTitle;
        this.footerNavigationTitle = footerNavigationTitle;
        this.metaDataTitle = metaDataTitle;
        this.description = description;
        this.href = href;
        this.navigationEntry = navigationEntry;
        this.media = media;
        this.external = external;
        this.active = active;
        this.children = new ArrayList<>();
        this.jcrPath = jcrPath;
        if (children != null) {
            this.children.addAll(children);
        }
    }

    public static NavigationBuilder builder() {

        return new NavigationBuilder();
    }

    public String getParentId() {

        return this.parentId;
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

    public Translation getMetaDataTitle() {

        return this.metaDataTitle;
    }

    public String getDescription() {

        return this.description;
    }

    public String getHref() {

        return StringUtils.defaultIfBlank(
                this.href,
                Stream.of(getJcrPath().split("/"))
                        .skip(4)
                        .collect(joining("/")) + ".html");
    }

    public boolean isNavigationEntry() {

        return this.navigationEntry;
    }

    public boolean isMedia() {

        return this.media;
    }

    public boolean isExternal() {

        return this.external;
    }

    public boolean isActive() {

        return this.active;
    }

    public List<Navigation> getChildren() {

        return new ArrayList<>(this.children);
    }

    public List<Navigation> getMutableChildren() {

        return this.children;
    }

    public String getJcrPath() {

        return this.jcrPath;
    }
}
