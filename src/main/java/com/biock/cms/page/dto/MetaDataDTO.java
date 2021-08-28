package com.biock.cms.page.dto;

import com.biock.cms.i18n.dto.TranslationDTO;
import com.biock.cms.page.MetaData;

import java.util.HashMap;
import java.util.Map;

public class MetaDataDTO {

    private Map<String, TranslationDTO> metaData;

    public static MetaDataDTO of(final MetaData entity) {

        final MetaDataDTO dto = new MetaDataDTO();
        if (entity != null) {
            final Map<String, TranslationDTO> metaData = new HashMap<>();
            entity.getMetaData().forEach((k, v) -> metaData.put(k, TranslationDTO.of(v)));
            dto.setMetaData(metaData);
        }
        return dto;
    }

    public Map<String, TranslationDTO> getMetaData() {

        return this.metaData;
    }

    public MetaDataDTO setMetaData(final Map<String, TranslationDTO> metaData) {

        this.metaData = metaData;
        return this;
    }
}
