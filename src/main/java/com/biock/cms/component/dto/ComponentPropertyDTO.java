package com.biock.cms.component.dto;

import com.biock.cms.component.ComponentProperty;
import com.biock.cms.shared.Label;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.NotNull;

public class ComponentPropertyDTO {

    @JsonValue
    private Object value;

    static ComponentPropertyDTO of(@NotNull final String language, @NotNull final ComponentProperty property) {

        final var dto = new ComponentPropertyDTO();
        if (property.getValue() instanceof Label) {
            dto.value = ((Label) property.getValue()).getText(language);
        } else {
            dto.value = property.getValue();
        }
        return dto;
    }

    public Object getValue() {

        return this.value;
    }
}
