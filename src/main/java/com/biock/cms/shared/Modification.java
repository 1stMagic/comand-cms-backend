package com.biock.cms.shared;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public class Modification implements ValueObject<Modification> {

    private final OffsetDateTime created;
    private final String createdBy;
    private final OffsetDateTime lastModified;
    private final String lastModifiedBy;

    public Modification(
            @NotNull final OffsetDateTime created,
            @NotNull final String createdBy,
            @NotNull final OffsetDateTime lastModified,
            @NotNull final String lastModifiedBy) {

        this.created = created;
        this.createdBy = createdBy;
        this.lastModified = lastModified;
        this.lastModifiedBy = lastModifiedBy;
    }

    public static Builder builder() {

        return new Builder();
    }

    public OffsetDateTime getCreated() {

        return this.created;
    }

    public String getCreatedBy() {

        return this.createdBy;
    }

    public OffsetDateTime getLastModified() {

        return this.lastModified;
    }

    public String getLastModifiedBy() {

        return this.lastModifiedBy;
    }

    @Override
    public int compareTo(@NotNull final Modification modification) {

        int c = this.getCreated().compareTo(modification.getCreated());
        if (c != 0) {
            return c;
        }
        c = this.getCreatedBy().compareTo(modification.getCreatedBy());
        if (c != 0) {
            return c;
        }
        c = this.getLastModified().compareTo(modification.getLastModified());
        if (c != 0) {
            return c;
        }
        return this.getLastModifiedBy().compareTo(modification.getLastModifiedBy());
    }

    public static final class Builder {

        private OffsetDateTime created;
        private String createdBy;
        private OffsetDateTime lastModified;
        private String lastModifiedBy;

        public Builder created(@NotNull final OffsetDateTime created) {

            this.created = created;
            return this;
        }

        public Builder createdBy(@NotNull final String createdBy) {

            this.createdBy = createdBy;
            return this;
        }

        public Builder lastModified(@NotNull final OffsetDateTime lastModified) {

            this.lastModified = lastModified;
            return this;
        }

        public Builder lastModifiedBy(@NotNull final String lastModifiedBy) {

            this.lastModifiedBy = lastModifiedBy;
            return this;
        }

        public Modification build() {

            return new Modification(this.created, this.createdBy, this.lastModified, this.lastModifiedBy);
        }
    }
}
