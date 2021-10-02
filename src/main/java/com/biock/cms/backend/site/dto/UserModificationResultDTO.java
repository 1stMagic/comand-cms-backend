package com.biock.cms.backend.site.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UserModificationResultDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String id;

    public String getId() {

        return this.id;
    }

    public UserModificationResultDTO setId(final String id) {

        this.id = id;
        return this;
    }
}
