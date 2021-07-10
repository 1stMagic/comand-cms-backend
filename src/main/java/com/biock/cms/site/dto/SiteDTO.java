package com.biock.cms.site.dto;

import com.biock.cms.site.Site;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SiteDTO {

    private String layout;
    private String theme;
    private String language;
    private String homePage;
    private List<SupportedLanguageDTO> supportedLanguages;
    private List<NavigationItemDTO> mainNavigation;
    private List<NavigationItemDTO> topNavigation;
    private List<NavigationItemDTO> footerNavigation;

    public static SiteDTO of(@NotNull final String language, @NotNull final Site site) {

        final var dto = new SiteDTO();
        dto.layout = site.getConfig().getLayout();
        dto.theme = site.getConfig().getTheme();
        dto.language = site.getConfig().getLanguage();
        dto.homePage = site.getConfig().getHomePage();
        dto.supportedLanguages = site.getConfig().getSupportedLanguages()
                .stream()
                .map(supportedLanguage -> SupportedLanguageDTO.of(language, supportedLanguage))
                .collect(toList());
        dto.mainNavigation = site.getMainNavigation().getItems()
                .stream()
                .map(item -> NavigationItemDTO.of(language, item))
                .collect(toList());
        dto.topNavigation = site.getTopNavigation().getItems()
                .stream()
                .map(item -> NavigationItemDTO.of(language, item))
                .collect(toList());
        dto.footerNavigation = site.getFooterNavigation().getItems()
                .stream()
                .map(item -> NavigationItemDTO.of(language, item))
                .collect(toList());
        return dto;
    }

    public String getLayout() {

        return this.layout;
    }

    public String getTheme() {

        return this.theme;
    }

    public String getLanguage() {

        return this.language;
    }

    public String getHomePage() {

        return this.homePage;
    }

    public List<SupportedLanguageDTO> getSupportedLanguages() {

        return this.supportedLanguages;
    }

    public List<NavigationItemDTO> getMainNavigation() {

        return this.mainNavigation;
    }

    public List<NavigationItemDTO> getTopNavigation() {

        return this.topNavigation;
    }

    public List<NavigationItemDTO> getFooterNavigation() {

        return this.footerNavigation;
    }
}
