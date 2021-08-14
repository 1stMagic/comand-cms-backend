package com.biock.cms.i18n;

import com.biock.cms.CmsApi;
import com.biock.cms.shared.ValueObject;
import com.biock.cms.i18n.builder.TranslationBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import static java.util.Collections.emptyMap;

public class Translation implements ValueObject<Translation> {

    private final Map<String, String> translations;

    public Translation(final Map<String, String> translations) {

        this.translations = new HashMap<>();
        if (translations != null) {
            this.translations.putAll(translations);
        }
    }

    public static TranslationBuilder builder() {

        return new TranslationBuilder();
    }

    public static Translation empty() {

        return new Translation(emptyMap());
    }

    public static Translation merge(final Translation... translations) {

        final Map<String, String> translationMap = new HashMap<>();
        for (final Translation translation : translations) {
            if (translation != null) {
                translation.getTranslations().forEach(translationMap::putIfAbsent);
            }
        }
        return new Translation(translationMap);
    }

    public Map<String, String> getTranslations() {

        return new HashMap<>(this.translations);
    }

    public String getTranslation(final String iso6391Code, final String... fallbackIso6391Codes) {

        if (this.translations.containsKey(iso6391Code)) {
            return this.translations.get(iso6391Code);
        }
        for (final String fallbackIso6391Code : fallbackIso6391Codes) {
            if (this.translations.containsKey(fallbackIso6391Code)) {
                return this.translations.get(fallbackIso6391Code);
            }
        }
        return this.translations.get(CmsApi.DEFAULT_LANGUAGE);
    }

    public boolean isEmpty() {

        return this.translations.isEmpty();
    }

    public Translation modify(final UnaryOperator<String> modifier) {

        this.translations.forEach((k, v) -> this.translations.put(k, modifier.apply(v)));
        return this;
    }

    @Override
    public int compareTo(final Translation other) {

        return 0;
    }
}
