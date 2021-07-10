package com.biock.cms.site;

import com.biock.cms.page.Page;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class SiteNavigation {

    private final List<SiteNavigationItem> items;

    public SiteNavigation() {

        this(new ArrayList<>());
    }

    public SiteNavigation(@NotNull final List<SiteNavigationItem> items) {

        this.items = items;
    }

    public static SiteNavigationItem createItem(@NotNull final String language, @NotNull final Page page) {

        return new SiteNavigationItem(
                page.getTitle().getText(language),
                page.getHref(),
                page.getConfig().getTarget(),
                page.getConfig().getIconClass(),
                new ArrayList<>());
    }

    public List<SiteNavigationItem> getItems() {

        return this.items;
    }
}
