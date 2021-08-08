package com.biock.cms.site.builder;

import com.biock.cms.shared.builder.Builder;
import com.biock.cms.site.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class LanguagesBuilder implements Builder<List<Language>> {

    private List<Language> languages;

    public LanguagesBuilder languages(final List<Language> languages) {

        this.languages = languages;
        return this;
    }

    public LanguagesBuilder language(final Language language) {

        if (this.languages == null) {
            this.languages = new ArrayList<>();
        }
        this.languages.add(language);
        return this;
    }

    @Override
    public LanguagesBuilder apply(final List<Language> other) {

        if (other != null) {
            return languages(other);
        }
        return this;
    }

    @Override
    public List<Language> build() {

        return Optional.ofNullable(this.languages).orElse(emptyList());
    }
}
