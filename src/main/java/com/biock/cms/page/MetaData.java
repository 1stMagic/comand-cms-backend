package com.biock.cms.page;

import com.biock.cms.i18n.Translation;
import com.biock.cms.page.builder.MetaDataBuilder;
import com.biock.cms.shared.ValueObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import static java.util.Collections.emptyMap;

public class MetaData implements ValueObject<MetaData> {

    private final Map<String, Translation> metaDataMap;

    public MetaData(final Map<String, Translation> metaData) {

        this.metaDataMap = new HashMap<>();
        if (metaData != null) {
            this.metaDataMap.putAll(metaData);
        }
    }

    public static MetaDataBuilder builder() {

        return new MetaDataBuilder();
    }

    public static MetaData empty() {

        return new MetaData(emptyMap());
    }

    public Map<String, Translation> getMetaData() {

        return new HashMap<>(this.metaDataMap);
    }

    public boolean isEmpty() {

        return this.metaDataMap.isEmpty();
    }

    public MetaData mofify(final String key, final UnaryOperator<String> modifier) {

        this.metaDataMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(key))
                .forEach(entry -> this.metaDataMap.put(entry.getKey(), entry.getValue().modify(modifier)));
        return this;
    }

    @Override
    public int compareTo(final MetaData metaData) {

        return 0;
    }
}
