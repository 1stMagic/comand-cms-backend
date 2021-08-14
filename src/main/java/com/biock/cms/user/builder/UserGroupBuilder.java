package com.biock.cms.user.builder;

import com.biock.cms.i18n.Translation;
import com.biock.cms.user.UserGroup;
import com.biock.cms.shared.builder.Builder;

public class UserGroupBuilder implements Builder<UserGroup> {

    private String id;
    private Translation name;
    private boolean active;

    public UserGroupBuilder id(final String id) {

        this.id = id;
        return this;
    }

    public UserGroupBuilder name(final Translation name) {

        this.name = name;
        return this;
    }

    public UserGroupBuilder active(final boolean active) {

        this.active = active;
        return this;
    }

    @Override
    public UserGroupBuilder apply(final UserGroup other) {

        if (other != null) {
            return id(other.getId())
                    .name(other.getName())
                    .active(other.isActive());
        }
        return this;
    }

    @Override
    public UserGroup build() {

        return new UserGroup(this.id, this.name, this.active);
    }
}
