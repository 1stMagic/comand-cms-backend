package com.biock.cms.component;

import com.biock.cms.component.dto.ComponentDTO;

import java.io.Serializable;

public interface Component extends Serializable {

    boolean isActive();
    String getComponentName();
    ComponentDTO toFrontendDTO(final String language, final String fallbackLanguage);
}
