package com.biock.cms.site;

import javax.validation.constraints.NotNull;

public class SiteConfig {

    private final String layout;
    private final String theme;
    private final String language;
    private final String homePage;
    private final String[] supportedLanguages;

    public SiteConfig(
            @NotNull final String layout,
            @NotNull final String theme,
            @NotNull final String language,
            @NotNull final String homePage,
            @NotNull final String[] supportedLanguages) {

        this.layout = layout;
        this.theme = theme;
        this.language = language;
        this.homePage = homePage;
        this.supportedLanguages = supportedLanguages;
    }

    public static Builder builder() {

        return new Builder();
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

    public String[] getSupportedLanguages() {

        return this.supportedLanguages;
    }

    public static final class Builder {

        private String layout;
        private String theme;
        private String language;
        private String homePage;
        private String[] supportedLanguages;

        public Builder layout(@NotNull final String layout) {

            this.layout = layout;
            return this;
        }

        public Builder theme(@NotNull final String theme) {

            this.theme = theme;
            return this;
        }

        public Builder language(@NotNull final String language) {

            this.language = language;
            return this;
        }

        public Builder homePage(@NotNull final String homePage) {

            this.homePage = homePage;
            return this;
        }

        public Builder supportedLanguage(@NotNull final String... supportedLanguages) {

            this.supportedLanguages = supportedLanguages;
            return this;
        }

        public SiteConfig build() {

            return new SiteConfig(this.layout, this.theme, this.language, this.homePage, this.supportedLanguages);
        }
    }
}
