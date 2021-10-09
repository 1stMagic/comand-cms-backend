package com.biock.cms.backend.site.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class UpdateUserGroupDTO {

    private Map<@NotBlank String, @NotNull String> name;
    private Boolean active;

    public Map<String, String> getName() {

        return this.name;
    }

    public UpdateUserGroupDTO setName(final Map<String, String> name) {

        this.name = name;
        return this;
    }

    public Boolean getActive() {

        return this.active;
    }

    public UpdateUserGroupDTO setActive(final Boolean active) {

        this.active = active;
        return this;
    }

    public boolean isEmpty() {

        return (this.name == null || this.name.isEmpty()) && this.active == null;
    }
}
