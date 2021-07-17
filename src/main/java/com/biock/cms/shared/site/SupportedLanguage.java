package com.biock.cms.shared.site;

import com.biock.cms.shared.Label;
import com.biock.cms.shared.ValueObject;

import javax.validation.constraints.NotNull;

public class SupportedLanguage implements ValueObject<SupportedLanguage> {

    private final String language;
    private final Label title;
    private final Label tooltip;

    public SupportedLanguage(
            @NotNull final String language,
            @NotNull final Label title,
            @NotNull final Label tooltip) {

        this.language = language;
        this.title = title;
        this.tooltip = tooltip;
    }

    public static Builder builder() {

        return new Builder();
    }

    public String getLanguage() {

        return this.language;
    }

    public Label getTitle() {

        return this.title;
    }

    public Label getTooltip() {

        return this.tooltip;
    }

    @Override
    public int compareTo(@NotNull final SupportedLanguage supportedLanguage) {

        int c = this.getLanguage().compareTo(supportedLanguage.getLanguage());
        if (c != 0) {
            return c;
        }
        c = this.getTitle().compareTo(supportedLanguage.getTitle());
        if (c != 0) {
            return c;
        }
        return this.getTooltip().compareTo(supportedLanguage.getTooltip());
    }

    public static final class Builder implements com.biock.cms.shared.Builder<SupportedLanguage> {

        private String language;
        private Label title;
        private Label tooltip;

        public Builder language(@NotNull final String language) {

            this.language = language;
            return this;
        }

        public Builder title(@NotNull final Label title) {

            this.title = title;
            return this;
        }

        public Builder tooltip(@NotNull final Label tooltip) {

            this.tooltip = tooltip;
            return this;
        }

        @Override
        public SupportedLanguage build() {

            return new SupportedLanguage(this.language, this.title, this.tooltip);
        }
    }
}
