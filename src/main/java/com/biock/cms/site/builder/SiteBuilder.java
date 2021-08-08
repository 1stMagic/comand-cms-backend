package com.biock.cms.site.builder;

import com.biock.cms.shared.ContactData;
import com.biock.cms.shared.EntityId;
import com.biock.cms.shared.Modification;
import com.biock.cms.shared.builder.Builder;
import com.biock.cms.site.Language;
import com.biock.cms.site.Site;
import com.biock.cms.user.User;
import com.biock.cms.user.UserGroup;

import java.util.List;

public class SiteBuilder implements Builder<Site> {
    
    private EntityId id;
    private String description;
    private Modification modification;
    private boolean active;
    private String layout;
    private String theme;
    private String homePage;
    private List<Language> languages;
    private ContactData contactData;
    private List<User> users;
    private List<UserGroup> userGroups;

    public SiteBuilder id(final EntityId id) {

        this.id = id;
        return this;
    }

    public SiteBuilder description(final String description) {

        this.description = description;
        return this;
    }

    public SiteBuilder modification(final Modification modification) {

        this.modification = modification;
        return this;
    }

    public SiteBuilder active(final boolean active) {

        this.active = active;
        return this;
    }

    public SiteBuilder layout(final String layout) {

        this.layout = layout;
        return this;
    }

    public SiteBuilder theme(final String theme) {

        this.theme = theme;
        return this;
    }

    public SiteBuilder homePage(final String homePage) {

        this.homePage = homePage;
        return this;
    }

    public SiteBuilder languages(final List<Language> languages) {

        this.languages = languages;
        return this;
    }

    public SiteBuilder contactData(final ContactData contactData) {

        this.contactData = contactData;
        return this;
    }

    public SiteBuilder users(final List<User> users) {

        this.users = users;
        return this;
    }

    public SiteBuilder userGroups(final List<UserGroup> userGroups) {

        this.userGroups = userGroups;
        return this;
    }

    @Override
    public SiteBuilder apply(final Site other) {

        if (other != null) {
            return id(other.getId())
                    .description(other.getDescription())
                    .modification(other.getModification())
                    .active(other.isActive())
                    .layout(other.getLayout())
                    .theme(other.getTheme())
                    .homePage(other.getHomePage())
                    .languages(other.getLanguages())
                    .contactData(other.getContactData())
                    .users(other.getUsers())
                    .userGroups(other.getUserGroups());
        }
        return this;
    }

    @Override
    public Site build() {

        return new Site(
                this.id,
                this.description,
                this.modification,
                this.active,
                this.layout,
                this.theme,
                this.homePage,
                this.languages,
                this.contactData,
                this.users,
                this.userGroups);
    }
}
