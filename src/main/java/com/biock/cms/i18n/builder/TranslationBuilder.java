package com.biock.cms.i18n.builder;

import com.biock.cms.i18n.Translation;
import com.biock.cms.shared.builder.Builder;

import java.util.HashMap;
import java.util.Map;

public class TranslationBuilder implements Builder<Translation> {

    private Map<String, String> translations;

    public TranslationBuilder translations(final Map<String, String> translations) {

        this.translations = translations;
        return this;
    }

    public TranslationBuilder translation(final String language, final String value) {

        if (this.translations == null) {
            this.translations = new HashMap<>();
        }
        this.translations.put(language, value);
        return this;
    }

    @Override
    public TranslationBuilder apply(final Translation other) {

        if (other != null) {
            return translations(other.getTranslations());
        }
        return this;
    }

    @Override
    public Translation build() {

        return new Translation(this.translations);
    }
}
