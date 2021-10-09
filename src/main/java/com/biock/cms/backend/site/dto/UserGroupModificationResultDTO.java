package com.biock.cms.backend.site.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UserGroupModificationResultDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String id;

    public String getId() {

        return this.id;
    }

    public UserGroupModificationResultDTO setId(final String id) {

        this.id = id;
        return this;
    }
}
