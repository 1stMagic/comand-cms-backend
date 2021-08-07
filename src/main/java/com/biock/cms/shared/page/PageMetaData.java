package com.biock.cms.shared.page;

import com.biock.cms.shared.ValueObject;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.Collections.emptyMap;

public class PageMetaData implements ValueObject<PageMetaData>, Iterable<Entry<String, String>> {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, String> metaData;

    public PageMetaData(final Map<String, String> metaData) {

        this.metaData = new HashMap<>();

        if (metaData != null) {
            this.metaData.putAll(metaData);
        }
    }

    public static Builder builder() {

        return new Builder();
    }

    public static PageMetaData empty() {

        return new PageMetaData(emptyMap());
    }

    public Map<String, String> getMetaData() {

        return new HashMap<>(this.metaData);
    }

    @Override
    public Iterator<Entry<String, String>> iterator() {

        return getMetaData().entrySet().iterator();
    }

    @Override
    public int compareTo(final PageMetaData pageMetaData) {

        return 0;
    }

    public static final class Builder implements com.biock.cms.shared.Builder<PageMetaData> {

        private final Map<String, String> metaData;

        private Builder() {

            this.metaData = new HashMap<>();
        }

        public Builder put(@NotNull final String name, @NotNull final String value) {

            this.metaData.put(name, value);
            return this;
        }

        @Override
        public PageMetaData build() {

            return new PageMetaData(this.metaData);
        }
    }
}
