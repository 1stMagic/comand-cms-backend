package com.biock.cms.frontend.site.dto;

import com.biock.cms.site.Language;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LanguageDTO {

    @JsonProperty("language")
    private String iso6391Code;
    @JsonProperty("title")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String tooltip;

    public static LanguageDTO of(final Language entity, final String language, final String fallbackLanguage) {

        if (entity == null) {
            return new LanguageDTO();
        }
        return new LanguageDTO()
                .setIso6391Code(entity.getIso6391Code())
                .setName(entity.getName().getTranslation(language, fallbackLanguage))
                .setTooltip(entity.getTooltip().getTranslation(language, fallbackLanguage));
    }

    public String getIso6391Code() {

        return this.iso6391Code;
    }

    public LanguageDTO setIso6391Code(final String iso6391Code) {

        this.iso6391Code = iso6391Code;
        return this;
    }

    public String getName() {

        return this.name;
    }

    public LanguageDTO setName(final String name) {

        this.name = name;
        return this;
    }

    public String getTooltip() {

        return this.tooltip;
    }

    public LanguageDTO setTooltip(final String tooltip) {

        this.tooltip = tooltip;
        return this;
    }
}
