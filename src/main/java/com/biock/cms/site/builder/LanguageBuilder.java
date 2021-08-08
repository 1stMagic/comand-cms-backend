package com.biock.cms.site.builder;

import com.biock.cms.shared.EntityId;
import com.biock.cms.shared.builder.Builder;
import com.biock.cms.site.Language;
import com.biock.cms.i18n.Translation;

public class LanguageBuilder implements Builder<Language> {

    private EntityId id;
    private String iso6391Code;
    private String iso6392Code;
    private Translation name;
    private Translation tooltip;
    private boolean active;
    private boolean defaultLanguage;

    public LanguageBuilder id(final EntityId id) {

        this.id = id;
        return this;
    }

    public LanguageBuilder iso6391Code(final String iso6391Code) {

        this.iso6391Code = iso6391Code;
        return this;
    }

    public LanguageBuilder iso6392Code(final String iso6392Code) {

        this.iso6392Code = iso6392Code;
        return this;
    }

    public LanguageBuilder name(final Translation name) {

        this.name = name;
        return this;
    }

    public LanguageBuilder tooltip(final Translation tooltip) {

        this.tooltip = tooltip;
        return this;
    }

    public LanguageBuilder active(final boolean active) {

        this.active = active;
        return this;
    }

    public LanguageBuilder defaultLanguage(final boolean defaultLanguage) {

        this.defaultLanguage = defaultLanguage;
        return this;
    }

    @Override
    public LanguageBuilder apply(final Language other) {

        if (other != null) {
            return id(other.getId())
                    .iso6391Code(other.getIso6391Code())
                    .iso6392Code(other.getIso6392Code())
                    .name(other.getName())
                    .tooltip(other.getTooltip())
                    .active(other.isActive())
                    .defaultLanguage(other.isDefaultLanguage());
        }
        return this;
    }

    @Override
    public Language build() {

        return new Language(
                this.id,
                this.iso6391Code,
                this.iso6392Code,
                this.name,
                this.tooltip,
                this.active,
                this.defaultLanguage);
    }
}
