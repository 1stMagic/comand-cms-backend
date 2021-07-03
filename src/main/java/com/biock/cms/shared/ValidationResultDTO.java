package com.biock.cms.shared;

import java.util.List;

public class ValidationResultDTO extends BaseResultDTO<ValidationResultDTO> {

    private List<ValidationErrorDTO> errors;

    public List<ValidationErrorDTO> getErrors() {

        return this.errors;
    }

    public ValidationResultDTO setErrors(final List<ValidationErrorDTO> errors) {

        this.errors = errors;
        return this;
    }
}
