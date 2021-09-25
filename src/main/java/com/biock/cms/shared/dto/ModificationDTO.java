package com.biock.cms.shared.dto;

import com.biock.cms.shared.Modification;

import java.time.OffsetDateTime;

public class ModificationDTO {

    private OffsetDateTime created;
    private String createdBy;
    private OffsetDateTime lastModified;
    private String lastModifiedBy;
    private String createdFormatted;
    private String lastModifiedFormatted;

    public static ModificationDTO of(final Modification entity) {

        if (entity == null) {
            return new ModificationDTO();
        }
        return new ModificationDTO()
                .setCreated(entity.getCreated())
                .setCreatedBy(entity.getCreatedBy())
                .setLastModified(entity.getLastModified())
                .setLastModifiedBy(entity.getLastModifiedBy())
                .setCreatedFormatted(entity.getCreatedFormatted())
                .setLastModifiedFormatted(entity.getLastModifiedFormatted());
    }

    public OffsetDateTime getCreated() {

        return this.created;
    }

    public ModificationDTO setCreated(final OffsetDateTime created) {

        this.created = created;
        return this;
    }

    public String getCreatedBy() {

        return this.createdBy;
    }

    public ModificationDTO setCreatedBy(final String createdBy) {

        this.createdBy = createdBy;
        return this;
    }

    public OffsetDateTime getLastModified() {

        return this.lastModified;
    }

    public ModificationDTO setLastModified(final OffsetDateTime lastModified) {

        this.lastModified = lastModified;
        return this;
    }

    public String getLastModifiedBy() {

        return this.lastModifiedBy;
    }

    public ModificationDTO setLastModifiedBy(final String lastModifiedBy) {

        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public String getCreatedFormatted() {

        return this.createdFormatted;
    }

    public ModificationDTO setCreatedFormatted(final String createdFormatted) {

        this.createdFormatted = createdFormatted;
        return this;
    }

    public String getLastModifiedFormatted() {

        return this.lastModifiedFormatted;
    }

    public ModificationDTO setLastModifiedFormatted(final String lastModifiedFormatted) {

        this.lastModifiedFormatted = lastModifiedFormatted;
        return this;
    }
}
