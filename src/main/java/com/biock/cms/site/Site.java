package com.biock.cms.site;

import com.biock.cms.page.Page;
import com.biock.cms.page.PageRepository;
import com.biock.cms.shared.AbstractEntity;
import com.biock.cms.shared.ContactData;
import com.biock.cms.shared.EntityId;
import com.biock.cms.shared.Modification;
import com.biock.cms.site.builder.NavigationItemBuilder;
import com.biock.cms.site.builder.SiteBuilder;
import com.biock.cms.user.User;
import com.biock.cms.user.UserGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.biock.cms.jcr.NodeFilters.onlyActive;
import static java.util.Collections.emptyList;

public class Site extends AbstractEntity<Site> {

    private final String description;
    private final Modification modification;
    private final boolean active;
    private final String layout;
    private final String theme;
    private final String homePage;
    private final List<Language> languages;
    private final ContactData contactData;
    private final List<User> users;
    private final List<UserGroup> userGroups;
    private final List<NavigationItem> topNavigation;
    private final List<NavigationItem> mainNavigation;
    private final List<NavigationItem> footerNavigation;

    public Site(
            final EntityId id,
            final String description,
            final Modification modification,
            final boolean active,
            final String layout,
            final String theme,
            final String homePage,
            final List<Language> languages,
            final ContactData contactData,
            final List<User> users,
            final List<UserGroup> userGroups) {

        super(id);
        this.description = description;
        this.modification = modification;
        this.active = active;
        this.layout = layout;
        this.theme = theme;
        this.homePage = homePage;
        this.languages = new ArrayList<>();
        this.contactData = contactData;
        this.users = new ArrayList<>();
        this.userGroups = new ArrayList<>();
        this.topNavigation = new ArrayList<>();
        this.mainNavigation = new ArrayList<>();
        this.footerNavigation = new ArrayList<>();

        if (languages != null) {
            this.languages.addAll(languages);
        }
        if (users != null) {
            this.users.addAll(users);
        }
        if (userGroups != null) {
            this.userGroups.addAll(userGroups);
        }
    }

    public static SiteBuilder builder() {

        return new SiteBuilder();
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

    public String getLayout() {

        return this.layout;
    }

    public String getTheme() {

        return this.theme;
    }

    public String getHomePage() {

        return this.homePage;
    }

    public List<Language> getLanguages() {

        return this.languages;
    }

    public ContactData getContactData() {

        return this.contactData;
    }

    public List<User> getUsers() {

        return this.users;
    }

    public List<UserGroup> getUserGroups() {

        return this.userGroups;
    }

    public List<NavigationItem> getTopNavigation() {

        return this.topNavigation;
    }

    public List<NavigationItem> getMainNavigation() {

        return this.mainNavigation;
    }

    public List<NavigationItem> getFooterNavigation() {

        return this.footerNavigation;
    }

    public Optional<String> getDefaultLanguage() {

        return Optional.ofNullable(this.languages)
                .orElse(emptyList())
                .stream()
                .filter(Language::isDefaultLanguage)
                .map(Language::getIso6391Code)
                .findFirst();
    }

    public Site buildNavigation(final PageRepository pageRepository) {

        this.topNavigation.clear();
        this.mainNavigation.clear();
        this.footerNavigation.clear();

        buildNavigation(
                pageRepository,
                pageRepository.getPagesOfSite(this, onlyActive()),
                this.topNavigation,
                this.mainNavigation,
                this.footerNavigation);

        return this;
    }

    private void buildNavigation(
            final PageRepository pageRepository,
            final List<Page> pages,
            final List<NavigationItem> topNavigation,
            final List<NavigationItem> mainNavigation,
            final List<NavigationItem> footerNavigation) {

        for (final Page page : pages) {
            final NavigationItemBuilder builder = NavigationItem.builder()
                    .href(page.getHref())
                    .target(page.getTarget())
                    .iconClass(page.getIconClass());
            buildNavigation(pageRepository, page, builder, topNavigation, mainNavigation, footerNavigation);
        }
    }

    private void buildNavigation(
            final PageRepository pageRepository,
            final Page page,
            final NavigationItemBuilder builder,
            final List<NavigationItem> topNavigation,
            final List<NavigationItem> mainNavigation,
            final List<NavigationItem> footerNavigation) {

        if (page.isShowInTopNavigation() && topNavigation != null) {
            topNavigation.add(builder.title(page.getTopNavigationTitle()).build());
        }
        if (page.isShowInMainNavigation() && mainNavigation != null) {
            mainNavigation.add(builder.title(page.getMainNavigationTitle()).build());
        }
        if (page.isShowInFooterNavigation() && footerNavigation != null) {
            footerNavigation.add(builder.title(page.getFooterNavigationTitle()).build());
        }
        buildNavigation(
                pageRepository,
                pageRepository.getChildrenOfPage(page, onlyActive()),
                page.isShowInTopNavigation() && topNavigation != null
                        ? topNavigation.get(topNavigation.size() - 1).getChildren()
                        : null,
                page.isShowInMainNavigation() && mainNavigation != null
                        ? mainNavigation.get(mainNavigation.size() - 1).getChildren()
                        : null,
                page.isShowInFooterNavigation() && footerNavigation != null
                        ? footerNavigation.get(footerNavigation.size() - 1).getChildren()
                        : null);
    }
}
