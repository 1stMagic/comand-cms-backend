package com.biock.cms.page.builder;

import com.biock.cms.i18n.Translation;
import com.biock.cms.page.MetaData;
import com.biock.cms.shared.builder.Builder;

import java.util.HashMap;
import java.util.Map;

public class MetaDataBuilder implements Builder<MetaData> {

    private Map<String, Translation> metaData;

    public MetaDataBuilder metaData(final Map<String, Translation> metaData) {

        this.metaData = metaData;
        return this;
    }

    public MetaDataBuilder metaDate(final String name, final Translation value) {

        if (this.metaData == null) {
            this.metaData = new HashMap<>();
        }
        this.metaData.put(name, value);
        return this;
    }

    @Override
    public MetaDataBuilder apply(final MetaData other) {

        if (other != null) {
            return metaData(other.getMetaData());
        }
        return this;
    }

    @Override
    public MetaData build() {

        return new MetaData(this.metaData);
    }
}
