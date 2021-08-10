package com.biock.cms.component;

import com.biock.cms.component.dto.ComponentDTO;

public interface Component {

    boolean isActive();
    String getComponentName();
    ComponentDTO toFrontendDTO(final String language, final String fallbackLanguage);
}
