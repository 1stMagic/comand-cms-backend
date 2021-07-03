package com.biock.cms.site.dto;

import com.biock.cms.shared.BaseResultDTO;

public class CreateSiteResultDTO extends BaseResultDTO<CreateSiteResultDTO> {

    private String siteId;

    public String getSiteId() {

        return this.siteId;
    }

    public CreateSiteResultDTO setSiteId(final String siteId) {

        this.siteId = siteId;
        return this;
    }
}
