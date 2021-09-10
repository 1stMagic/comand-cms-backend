package com.biock.cms.backend.site.dto;

import com.biock.cms.user.UserGroup;

public class UserGroupDTO {

    private String id;
    private String name;
    private boolean active;

    public static UserGroupDTO of(final UserGroup entity, final String language, final String fallbackLanguage) {

        if (entity == null) {
            return new UserGroupDTO();
        }
        return new UserGroupDTO()
                .setId(entity.getId())
                .setName(entity.getName().getTranslation(language, fallbackLanguage))
                .setActive(entity.isActive());
    }

    public String getId() {

        return this.id;
    }

    public UserGroupDTO setId(final String id) {

        this.id = id;
        return this;
    }

    public String getName() {

        return this.name;
    }

    public UserGroupDTO setName(final String name) {

        this.name = name;
        return this;
    }

    public boolean isActive() {

        return this.active;
    }

    public UserGroupDTO setActive(final boolean active) {

        this.active = active;
        return this;
    }
}
