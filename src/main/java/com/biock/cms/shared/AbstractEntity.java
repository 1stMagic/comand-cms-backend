package com.biock.cms.shared;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class AbstractEntity<T> implements Entity<T> {

    private final EntityId id;

    protected AbstractEntity(final EntityId id) {

        this.id = requireNonNull(id, "id is null");
    }

    @Override
    public EntityId getId() {

        return this.id;
    }

    @Override
    public int compareTo(final Entity<T> other) {

        return getId().compareTo(other.getId());
    }

    @Override
    public boolean equals(final Object other) {

        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        //noinspection unchecked
        return this.id.equals(((Entity<T>) other).getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id);
    }

}
