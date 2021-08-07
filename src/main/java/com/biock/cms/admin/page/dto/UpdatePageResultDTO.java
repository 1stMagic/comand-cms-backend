package com.biock.cms.admin.page.dto;

import com.biock.cms.site.dto.ResultDTO;

public class UpdatePageResultDTO extends ResultDTO<UpdatePageResultDTO> {

    private String id;

    public String getId() {

        return this.id;
    }

    public UpdatePageResultDTO setId(final String id) {

        this.id = id;
        return this;
    }
}
