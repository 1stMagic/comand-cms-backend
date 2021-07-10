package com.biock.cms.site.dto;

import com.biock.cms.site.SupportedLanguage;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

public class SupportedLanguageDTO {

    private String language;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String title;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String tooltip;

    public static SupportedLanguageDTO of(@NotNull final String language, @NotNull final SupportedLanguage supportedLanguage) {

        final var dto = new SupportedLanguageDTO();
        dto.language = supportedLanguage.getLanguage();
        dto.title = supportedLanguage.getTitle().getText(language);
        dto.tooltip = supportedLanguage.getTooltip().getText(language);
        return dto;
    }

    public String getLanguage() {

        return this.language;
    }

    public String getTitle() {

        return this.title;
    }

    public String getTooltip() {

        return this.tooltip;
    }
}
