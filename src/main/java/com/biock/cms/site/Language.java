package com.biock.cms.site;

import com.biock.cms.shared.AbstractEntity;
import com.biock.cms.shared.EntityId;
import com.biock.cms.i18n.Translation;
import com.biock.cms.site.builder.LanguageBuilder;

public class Language extends AbstractEntity<Language> {

    private final String iso6391Code;
    private final String iso6392Code;
    private final Translation name;
    private final Translation tooltip;
    private final boolean active;
    private final boolean defaultLanguage;

    public Language(
            final EntityId id,
            final String iso6391Code,
            final String iso6392Code,
            final Translation name,
            final Translation tooltip,
            final boolean active,
            final boolean defaultLanguage) {

        super(id);
        this.iso6391Code = iso6391Code;
        this.iso6392Code = iso6392Code;
        this.name = name;
        this.tooltip = tooltip;
        this.active = active;
        this.defaultLanguage = defaultLanguage;
    }

    public static LanguageBuilder builder() {

        return new LanguageBuilder();
    }

    public String getIso6391Code() {

        return this.iso6391Code;
    }

    public String getIso6392Code() {

        return this.iso6392Code;
    }

    public Translation getName() {

        return this.name;
    }

    public Translation getTooltip() {

        return this.tooltip;
    }

    public boolean isActive() {

        return this.active;
    }

    public boolean isDefaultLanguage() {

        return this.defaultLanguage;
    }
}
