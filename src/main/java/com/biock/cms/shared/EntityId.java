package com.biock.cms.shared;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class EntityId implements ValueObject<EntityId> {

    private final String id;

    public EntityId(final String id) {

        this.id = requireNonNull(id, "id is null");
    }

    public static EntityId create() {

        return new EntityId(UUID.randomUUID().toString());
    }

    public String getId() {

        return this.id;
    }

    @Override
    public int compareTo(final EntityId other) {

        return this.id.compareTo(other.id);
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EntityId entityId = (EntityId) o;
        return id.equals(entityId.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
