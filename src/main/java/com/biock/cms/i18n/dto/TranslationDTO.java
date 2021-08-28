package com.biock.cms.i18n.dto;

import com.biock.cms.i18n.Translation;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public class TranslationDTO {

    @JsonIgnore
    private Map<String, String> translations;

    public static TranslationDTO of(final Translation entity) {

        if (entity == null) {
            return new TranslationDTO();
        }
        return new TranslationDTO().setTranslations(entity.getTranslations());
    }

    @JsonAnyGetter
    public Map<String, String> getTranslations() {

        return this.translations;
    }

    public TranslationDTO setTranslations(final Map<String, String> translations) {

        this.translations = translations;
        return this;
    }
}
