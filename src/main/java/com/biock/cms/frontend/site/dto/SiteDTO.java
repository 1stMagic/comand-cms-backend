package com.biock.cms.frontend.site.dto;

import com.biock.cms.CmsApi;
import com.biock.cms.site.Site;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class SiteDTO {

    private String layout;
    private String theme;
    private String language;
    private String homePage;
    @JsonProperty("supportedLanguages")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<LanguageDTO> languages;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ContactDataDTO contactData;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<NavigationItemDTO> topNavigation;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<NavigationItemDTO> mainNavigation;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<NavigationItemDTO> footerNavigation;

    public static SiteDTO of(final Site entity, final String language, final String fallbackLanguage) {

        if (entity == null) {
            return new SiteDTO();
        }
        return new SiteDTO()
                .setLayout(entity.getLayout())
                .setTheme(entity.getTheme())
                .setLanguage(entity.getDefaultLanguage().orElse(CmsApi.DEFAULT_LANGUAGE))
                .setHomePage(entity.getHomePage())
                .setLanguages(Optional.ofNullable(entity.getLanguages())
                        .orElse(emptyList())
                        .stream()
                        .map(l -> LanguageDTO.of(l, language, fallbackLanguage))
                        .collect(toList()))
                .setContactData(ContactDataDTO.of(entity.getContactData()))
                .setTopNavigation(Optional.ofNullable(entity.getTopNavigation())
                        .orElse(emptyList())
                        .stream()
                        .map(item -> NavigationItemDTO.of(item, language, fallbackLanguage))
                        .collect(toList()))
                .setMainNavigation(Optional.ofNullable(entity.getMainNavigation())
                        .orElse(emptyList())
                        .stream()
                        .map(item -> NavigationItemDTO.of(item, language, fallbackLanguage))
                        .collect(toList()))
                .setFooterNavigation(Optional.ofNullable(entity.getFooterNavigation())
                        .orElse(emptyList())
                        .stream()
                        .map(item -> NavigationItemDTO.of(item, language, fallbackLanguage))
                        .collect(toList()));
    }

    public String getLayout() {

        return this.layout;
    }

    public SiteDTO setLayout(final String layout) {

        this.layout = layout;
        return this;
    }

    public String getTheme() {

        return this.theme;
    }

    public SiteDTO setTheme(final String theme) {

        this.theme = theme;
        return this;
    }

    public String getLanguage() {

        return this.language;
    }

    public SiteDTO setLanguage(final String language) {

        this.language = language;
        return this;
    }

    public String getHomePage() {

        return this.homePage;
    }

    public SiteDTO setHomePage(final String homePage) {

        this.homePage = homePage;
        return this;
    }

    public List<LanguageDTO> getLanguages() {

        return this.languages;
    }

    public SiteDTO setLanguages(final List<LanguageDTO> languages) {

        this.languages = languages;
        return this;
    }

    public ContactDataDTO getContactData() {

        return this.contactData;
    }

    public SiteDTO setContactData(final ContactDataDTO contactData) {

        this.contactData = contactData;
        return this;
    }

    public List<NavigationItemDTO> getMainNavigation() {

        return this.mainNavigation;
    }

    public SiteDTO setMainNavigation(final List<NavigationItemDTO> mainNavigation) {

        this.mainNavigation = mainNavigation;
        return this;
    }

    public List<NavigationItemDTO> getTopNavigation() {

        return this.topNavigation;
    }

    public SiteDTO setTopNavigation(final List<NavigationItemDTO> topNavigation) {

        this.topNavigation = topNavigation;
        return this;
    }

    public List<NavigationItemDTO> getFooterNavigation() {

        return this.footerNavigation;
    }

    public SiteDTO setFooterNavigation(final List<NavigationItemDTO> footerNavigation) {

        this.footerNavigation = footerNavigation;
        return this;
    }
}
