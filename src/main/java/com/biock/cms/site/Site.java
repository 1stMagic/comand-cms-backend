package com.biock.cms.site;

import com.biock.cms.page.Page;
import com.biock.cms.page.PageRepository;
import com.biock.cms.shared.Descriptor;
import com.biock.cms.shared.Modification;
import com.biock.cms.shared.site.SiteConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public class Site {

    private static final Logger LOG = LoggerFactory.getLogger(Site.class);

    private final Descriptor descriptor;
    private final Modification modification;
    private final boolean active;
    private final SiteConfig config;
    private final SiteNavigation mainNavigation;
    private final SiteNavigation topNavigation;
    private final SiteNavigation footerNavigation;

    public Site(
            @NotNull final Descriptor descriptor,
            @NotNull final Modification modification,
            final boolean active,
            @NotNull final SiteConfig config) {

        this.descriptor = descriptor;
        this.modification = modification;
        this.active = active;
        this.config = config;
        this.mainNavigation = new SiteNavigation();
        this.topNavigation = new SiteNavigation();
        this.footerNavigation = new SiteNavigation();
    }

    public static Builder builder() {

        return new Builder();
    }

    public Descriptor getDescriptor() {

        return this.descriptor;
    }

    public Modification getModification() {

        return this.modification;
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

    public void buildNavigation(@NotNull final String language, @NotNull final PageRepository pageRepository) {

        LOG.info("{}.buildNavigation()", getClass().getSimpleName());

        final List<Page> pages = pageRepository.getPages(this, true);

        LOG.info("Pages: {}", pages.size());

        if (LOG.isTraceEnabled()) {
            pages.forEach(p -> LOG.trace("Page: {}", p.getDescriptor().getName()));
        }

        for (final Page page : pages) {
            final SiteNavigationItem item = SiteNavigation.createItem(language, page);
            if (page.getConfig().isShowInMainNavigation()) {
                this.mainNavigation.getItems().add(item);
            }
            if (page.getConfig().isShowInTopNavigation()) {
                this.topNavigation.getItems().add(item);
            }
            if (page.getConfig().isShowInFooterNavigation()) {
                this.footerNavigation.getItems().add(item);
            }
            buildNavigation(
                    language,
                    pageRepository,
                    page,
                    item,
                    this.topNavigation.getItems(),
                    this.footerNavigation.getItems());
        }
    }

    private void buildNavigation(
            @NotNull final String language,
            @NotNull final PageRepository pageRepository,
            @NotNull final Page page,
            @NotNull final SiteNavigationItem item,
            @NotNull final List<SiteNavigationItem> topNavigationItems,
            @NotNull final List<SiteNavigationItem> footerNavigationItems) {

        LOG.debug("{}.buildNavigation(page = {})", getClass().getSimpleName(), page.getDescriptor().getName());

        final List<Page> pages = pageRepository.getPages(page, true);

        LOG.debug("Pages: {}", pages.size());

        if (LOG.isTraceEnabled()) {
            pages.forEach(p -> LOG.trace("Page: {}", p.getDescriptor().getName()));
        }

        for (final Page childPage : pages) {
            final SiteNavigationItem childItem = SiteNavigation.createItem(language, childPage);
            if (childPage.getConfig().isShowInMainNavigation()) {
                item.getChildren().add(childItem);
            }
            if (childPage.getConfig().isShowInTopNavigation()) {
                topNavigationItems.add(childItem);
            }
            if (childPage.getConfig().isShowInFooterNavigation()) {
                footerNavigationItems.add(childItem);
            }
            buildNavigation(language, pageRepository, childPage, childItem, topNavigationItems, footerNavigationItems);
        }
    }

    public static final class Builder implements com.biock.cms.shared.Builder<Site> {

        private Descriptor descriptor;
        private Modification modification;
        private boolean active;
        private SiteConfig config;

        public Builder descriptor(@NotNull final Descriptor descriptor) {

            this.descriptor = descriptor;
            return this;
        }

        public Builder modification(@NotNull final Modification modification) {

            this.modification = modification;
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

        @Override
        public Site build() {

            return new Site(
                    this.descriptor,
                    this.modification,
                    this.active,
                    Optional.ofNullable(this.config).orElse(new DefaultSiteConfig()));
        }
    }
}
