package com.biock.cms.backend.site.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class CreateUserGroupDTO {

    @NotEmpty
    private Map<@NotBlank String, @NotNull String> name;

    public Map<String, String> getName() {

        return this.name;
    }

    public CreateUserGroupDTO setName(final Map<String, String> name) {

        this.name = name;
        return this;
    }
}
