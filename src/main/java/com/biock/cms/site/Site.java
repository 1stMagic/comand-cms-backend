package com.biock.cms.site;

import com.biock.cms.page.Page;
import com.biock.cms.page.PageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Site {

    private static final Logger LOG = LoggerFactory.getLogger(Site.class);

    @NotEmpty(message = "Please provide a name")
    private final String name;
    @NotEmpty(message = "Please provide a title")
    private final String title;
    private final String description;
    @NotEmpty(message = "Please provide a createdBy")
    private final OffsetDateTime created;
    private final String createdBy;
    private final OffsetDateTime lastModified;
    private final String lastModifiedBy;
    private final boolean active;
    private final SiteConfig config;
    private final SiteNavigation mainNavigation;
    private final SiteNavigation topNavigation;
    private final SiteNavigation footerNavigation;

    public Site(
            @NotNull final String name,
            @NotNull final String title,
            @NotNull final String description,
            @NotNull final OffsetDateTime created,
            @NotNull final String createdBy,
            @NotNull final OffsetDateTime lastModified,
            @NotNull final String lastModifiedBy,
            final boolean active,
            @NotNull final SiteConfig config) {

        this.name = name;
        this.title = title;
        this.description = description;
        this.created = created;
        this.createdBy = createdBy;
        this.lastModified = lastModified;
        this.lastModifiedBy = lastModifiedBy;
        this.active = active;
        this.config = config;
        this.mainNavigation = new SiteNavigation();
        this.topNavigation = new SiteNavigation();
        this.footerNavigation = new SiteNavigation();
    }

    public static Builder builder() {

        return new Builder();
    }

    public String getName() {

        return this.name;
    }

    public String getTitle() {

        return this.title;
    }

    public String getDescription() {

        return this.description;
    }

    public OffsetDateTime getCreated() {

        return this.created;
    }

    public String getCreatedBy() {

        return this.createdBy;
    }

    public OffsetDateTime getLastModified() {

        return this.lastModified;
    }

    public String getLastModifiedBy() {

        return this.lastModifiedBy;
    }

    public boolean isActive() {

        return this.active;
    }

    public SiteConfig getConfig() {

        return this.config;
    }

    public SiteNavigation getMainNavigation() {

        return this.mainNavigation;
    }

    public SiteNavigation getTopNavigation() {

        return this.topNavigation;
    }

    public SiteNavigation getFooterNavigation() {

        return this.footerNavigation;
    }

    public void buildNavigation(@NotNull final PageRepository pageRepository) {

        LOG.info("{}.buildNavigation()", getClass().getSimpleName());

        final List<Page> pages = pageRepository.getPages(this, true);

        LOG.info("Pages: {}", pages.size());

        if (LOG.isTraceEnabled()) {
            pages.forEach(p -> LOG.trace("Page: {}", p.getName()));
        }

        for (final Page page : pages) {
            final SiteNavigationItem item = SiteNavigation.createItem(page);
            if (page.getConfig().isShowInMainNavigation()) {
                this.mainNavigation.getItems().add(item);
            }
            if (page.getConfig().isShowInTopNavigation()) {
                this.topNavigation.getItems().add(item);
            }
            if (page.getConfig().isShowInFooterNavigation()) {
                this.footerNavigation.getItems().add(item);
            }
            buildNavigation(pageRepository, page, item, this.topNavigation.getItems(), this.footerNavigation.getItems());
        }
    }

    private void buildNavigation(
            @NotNull final PageRepository pageRepository,
            @NotNull final Page page,
            @NotNull final SiteNavigationItem item,
            @NotNull final List<SiteNavigationItem> topNavigationItems,
            @NotNull final List<SiteNavigationItem> footerNavigationItems) {

        LOG.debug("{}.buildNavigation(page = {})", getClass().getSimpleName(), page.getName());

        final List<Page> pages = pageRepository.getPages(page, true);

        LOG.debug("Pages: {}", pages.size());

        if (LOG.isTraceEnabled()) {
            pages.forEach(p -> LOG.trace("Page: {}", p.getName()));
        }

        for (final Page childPage : pages) {
            final SiteNavigationItem childItem = SiteNavigation.createItem(childPage);
            if (childPage.getConfig().isShowInMainNavigation()) {
                item.getChildren().add(childItem);
            }
            if (childPage.getConfig().isShowInTopNavigation()) {
                topNavigationItems.add(childItem);
            }
            if (childPage.getConfig().isShowInFooterNavigation()) {
                footerNavigationItems.add(childItem);
            }
            buildNavigation(pageRepository, childPage, childItem, topNavigationItems, footerNavigationItems);
        }
    }

    public static final class Builder {

        private String name;
        private String title;
        private String description;
        private OffsetDateTime created;
        private String createdBy;
        private OffsetDateTime lastModified;
        private String lastModifiedBy;
        private boolean active;
        private SiteConfig config;

        public Builder name(@NotNull final String name) {

            this.name = name;
            return this;
        }

        public Builder title(@NotNull final String title) {

            this.title = title;
            return this;
        }

        public Builder description(@NotNull final String description) {

            this.description = description;
            return this;
        }

        public Builder created(@NotNull final OffsetDateTime created) {

            this.created = created;
            return this;
        }

        public Builder createdBy(@NotNull final String createdBy) {

            this.createdBy = createdBy;
            return this;
        }

        public Builder lastModified(@NotNull final OffsetDateTime lastModified) {

            this.lastModified = lastModified;
            return this;
        }

        public Builder lastModifiedBy(@NotNull final String lastModifiedBy) {

            this.lastModifiedBy = lastModifiedBy;
            return this;
        }

        public Builder active(final boolean active) {

            this.active = active;
            return this;
        }

        public Builder config(@NotNull final SiteConfig config) {

            this.config = config;
            return this;
        }

        public Site build() {

            return new Site(
                    this.name,
                    this.title,
                    this.description,
                    this.created,
                    this.createdBy,
                    this.lastModified,
                    this.lastModifiedBy,
                    this.active,
                    Optional.ofNullable(this.config).orElse(new DefaultSiteConfig()));
        }
    }
}
