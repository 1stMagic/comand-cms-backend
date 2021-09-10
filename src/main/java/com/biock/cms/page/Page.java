package com.biock.cms.page;

import com.biock.cms.CmsMetaData;
import com.biock.cms.component.Component;
import com.biock.cms.i18n.Translation;
import com.biock.cms.page.builder.PageBuilder;
import com.biock.cms.shared.AbstractEntity;
import com.biock.cms.shared.Modification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class Page extends AbstractEntity<Page> {

    private final String name;
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
    private final boolean navigationEntry;
    private final boolean media;
    private final boolean external;
    private final String href;
    private final String target;
    private final String[] requiredGroups;
    private final MetaData metaData;
    private final String jcrPath;
    private final List<Component> components;

    public Page(
            final String id,
            final String name,
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
            final boolean navigationEntry,
            final boolean media,
            final boolean external,
            final String href,
            final String target,
            final String[] requiredGroups,
            final MetaData metaData,
            final String jcrPath,
            final List<Component> components) {

        super(id);
        this.name = name;
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
        this.navigationEntry = navigationEntry;
        this.media = media;
        this.external = external;
        this.href = href;
        this.target = target;
        this.requiredGroups = requiredGroups;
        this.metaData = metaData;
        this.jcrPath = jcrPath;
        this.components = new ArrayList<>();
        if (components != null) {
            this.components.addAll(components);
        }
    }

    public static PageBuilder builder() {

        return new PageBuilder();
    }

    public String getName() {

        return this.name;
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

    public boolean isNavigationEntry() {

        return this.navigationEntry;
    }

    public boolean isMedia() {

        return this.media;
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

    public String[] getRequiredGroups() {

        return this.requiredGroups;
    }

    public MetaData getMetaData() {

        return this.metaData;
    }

    public String getJcrPath() {

        return this.jcrPath;
    }

    public List<Component> getComponents() {

        return new ArrayList<>(this.components);
    }

    @JsonIgnore
    public boolean hasTopNavigationTitle() {

        return this.topNavigationTitle != null && !this.topNavigationTitle.isEmpty();
    }

    @JsonIgnore
    public boolean hasMainNavigationTitle() {

        return this.mainNavigationTitle != null && !this.mainNavigationTitle.isEmpty();
    }

    @JsonIgnore
    public boolean hasFooterNavigationTitle() {

        return this.footerNavigationTitle != null && !this.footerNavigationTitle.isEmpty();
    }

    @JsonIgnore
    public boolean hasMetaData() {

        return this.metaData != null && !this.metaData.isEmpty();
    }

    public String buildHref() {

        return StringUtils.defaultIfBlank(
                this.href,
                Stream.of(getJcrPath().split("/"))
                        .skip(4)
                        .collect(joining("/")) + ".html");
    }

    public Translation buildTopNavigationTitle() {

        return Translation.merge(
                this.topNavigationTitle,
                this.mainNavigationTitle,
                Optional.ofNullable(this.metaData).orElse(MetaData.empty()).getMetaData().get(CmsMetaData.TITLE));
    }

    public Translation buildMainNavigationTitle() {

        return Translation.merge(
                this.mainNavigationTitle,
                Optional.ofNullable(this.metaData).orElse(MetaData.empty()).getMetaData().get(CmsMetaData.TITLE));
    }

    public Translation buildFooterNavigationTitle() {

        return Translation.merge(
                this.footerNavigationTitle,
                this.mainNavigationTitle,
                Optional.ofNullable(this.metaData).orElse(MetaData.empty()).getMetaData().get(CmsMetaData.TITLE));
    }
}
