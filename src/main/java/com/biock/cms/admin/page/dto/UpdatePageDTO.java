package com.biock.cms.admin.page.dto;

public class UpdatePageDTO {

    private boolean active;

    public boolean isActive() {

        return this.active;
    }

    public UpdatePageDTO setActive(final boolean active) {

        this.active = active;
        return this;
    }
}
