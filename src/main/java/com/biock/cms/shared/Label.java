package com.biock.cms.shared;

import com.biock.cms.utils.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import static java.util.Collections.emptyMap;

public class Label implements ValueObject<Label> {

    public static final String FALLBACK_LANGUAGE = "_";

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, String> texts;

    public Label(@NotNull final Map<String, String> texts) {

        this.texts = CollectionUtils.copy(texts);
    }

    public static Builder builder() {

        return new Builder();
    }

    public static Label empty() {

        return new Label(emptyMap());
    }

    public boolean isEmpty() {

        return this.texts.isEmpty();
    }

    public Map<String, String> getTexts() {

        return CollectionUtils.copy(this.texts);
    }

    public String getText(final String language) {

        if (this.texts.containsKey(language)) {
            return this.texts.get(language);
        }
        return this.texts.get(FALLBACK_LANGUAGE);
    }

    @JsonIgnore
    public List<String> getLanguages() {

        return new ArrayList<>(this.texts.keySet());
    }

    public Label copy(final String format) {

        final Map<String, String> mappedTexts = new HashMap<>();
        final UnaryOperator<String> textMapper = text -> String.format(format, text);
        getTexts().forEach((key, value) -> mappedTexts.put(key, textMapper.apply(value)));
        return new Label(mappedTexts);
    }

    @Override
    public int compareTo(@NotNull final Label label) {

        return Integer.compare(this.getTexts().size(), label.getTexts().size());
    }

    public static final class Builder implements com.biock.cms.shared.Builder<Label> {

        private final Map<String, String> texts;

        public Builder() {

            this.texts = new HashMap<>();
        }

        public Builder text(@NotNull final String language, @NotNull final String text) {

            this.texts.put(StringUtils.defaultIfBlank(language, FALLBACK_LANGUAGE), text);
            return this;
        }

        @Override
        public Label build() {

            return new Label(this.texts);
        }
    }
}
