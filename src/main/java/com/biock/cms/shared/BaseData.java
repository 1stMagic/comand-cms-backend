package com.biock.cms.shared;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public class BaseData {

    private final String name;
    private final String title;
    private final String description;
    private final OffsetDateTime created;
    private final String createdBy;
    private final OffsetDateTime lastModified;
    private final String lastModifiedBy;
    private final boolean active;

    public BaseData(
            @NotNull final String name, 
            @NotNull final String title, 
            @NotNull final String description, 
            @NotNull final OffsetDateTime created, 
            @NotNull final String createdBy, 
            @NotNull final OffsetDateTime lastModified, 
            @NotNull final String lastModifiedBy, 
            final boolean active) {

        this.name = name;
        this.title = title;
        this.description = description;
        this.created = created;
        this.createdBy = createdBy;
        this.lastModified = lastModified;
        this.lastModifiedBy = lastModifiedBy;
        this.active = active;
    }

    public static Builder builder() {

        return new Builder();
    }

    public String getName() {

        return this.name;
    }

    public String getTitle() {

        return this.title;
    }

    public String getDescription() {

        return this.description;
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

    public boolean isActive() {

        return this.active;
    }

    public static final class Builder implements com.biock.cms.shared.Builder<BaseData> {

        private String name;
        private String title;
        private String description;
        private OffsetDateTime created;
        private String createdBy;
        private OffsetDateTime lastModified;
        private String lastModifiedBy;
        private boolean active;

        public Builder name(@NotNull final String name) {

            this.name = name;
            return this;
        }

        public Builder title(@NotNull final String title) {

            this.title = title;
            return this;
        }

        public Builder description(@NotNull final String description) {

            this.description = description;
            return this;
        }

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

        public Builder active(final boolean active) {

            this.active = active;
            return this;
        }

        @Override
        public BaseData build() {

            return new BaseData(
                    this.name,
                    this.title,
                    this.description,
                    this.created,
                    this.createdBy,
                    this.lastModified,
                    this.lastModifiedBy,
                    this.active);
        }
    }
}
