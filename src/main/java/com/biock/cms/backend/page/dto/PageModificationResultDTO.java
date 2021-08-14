package com.biock.cms.backend.page.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class PageModificationResultDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String id;

    public String getId() {

        return this.id;
    }

    public PageModificationResultDTO setId(final String id) {

        this.id = id;
        return this;
    }
}
