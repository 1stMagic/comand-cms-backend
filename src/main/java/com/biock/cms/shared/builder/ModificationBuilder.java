package com.biock.cms.shared.builder;

import com.biock.cms.shared.Modification;

import java.time.OffsetDateTime;

public class ModificationBuilder implements Builder<Modification> {

    private OffsetDateTime created;
    private String createdBy;
    private OffsetDateTime lastModified;
    private String lastModifiedBy;

    public ModificationBuilder created(final OffsetDateTime created) {

        this.created = created;
        return this;
    }

    public ModificationBuilder createdBy(final String createdBy) {

        this.createdBy = createdBy;
        return this;
    }

    public ModificationBuilder lastModified(final OffsetDateTime lastModified) {

        this.lastModified = lastModified;
        return this;
    }

    public ModificationBuilder lastModifiedBy(final String lastModifiedBy) {

        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    @Override
    public ModificationBuilder apply(final Modification other) {

        if (other != null) {
            return created(other.getCreated())
                    .createdBy(other.getCreatedBy())
                    .lastModified(other.getLastModified())
                    .lastModifiedBy(other.getLastModifiedBy());
        }
        return this;
    }

    @Override
    public Modification build() {

        return new Modification(this.created, this.createdBy, this.lastModified, this.lastModifiedBy);
    }
}
