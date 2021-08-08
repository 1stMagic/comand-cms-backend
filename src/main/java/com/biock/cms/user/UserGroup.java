package com.biock.cms.user;

import com.biock.cms.shared.AbstractEntity;
import com.biock.cms.shared.EntityId;
import com.biock.cms.i18n.Translation;
import com.biock.cms.user.builder.UserGroupBuilder;

public class UserGroup extends AbstractEntity<UserGroup> {

    private final Translation name;
    private final boolean active;

    public UserGroup(final EntityId id, final Translation name, final boolean active) {

        super(id);
        this.name = name;
        this.active = active;
    }

    public static UserGroupBuilder builder() {

        return new UserGroupBuilder();
    }

    public Translation getName() {

        return this.name;
    }

    public boolean isActive() {

        return this.active;
    }

}
