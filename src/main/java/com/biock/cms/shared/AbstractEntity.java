package com.biock.cms.shared;

import java.util.Objects;

public abstract class AbstractEntity<T> implements Entity<T> {

    private final String id;

    protected AbstractEntity(final String id) {

        this.id = id;
    }

    @Override
    public String getId() {

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
        return ((AbstractEntity<?>) other).getId().equals(this.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id);
    }
}
