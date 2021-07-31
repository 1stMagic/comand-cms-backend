package com.biock.cms.admin.page.dto;

import com.biock.cms.site.dto.ResultDTO;

public class DeletePageResultDTO extends ResultDTO<DeletePageResultDTO> {

    private String id;

    public String getId() {

        return this.id;
    }

    public DeletePageResultDTO setId(final String id) {

        this.id = id;
        return this;
    }
}
