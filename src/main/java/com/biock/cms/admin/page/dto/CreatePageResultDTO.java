package com.biock.cms.admin.page.dto;

import com.biock.cms.site.dto.ResultDTO;

public class CreatePageResultDTO extends ResultDTO<CreatePageResultDTO> {

    private String id;

    public String getId() {

        return this.id;
    }

    public CreatePageResultDTO setId(final String id) {

        this.id = id;
        return this;
    }
}
