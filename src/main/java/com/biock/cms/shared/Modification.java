package com.biock.cms.shared;

import com.biock.cms.shared.builder.ModificationBuilder;
import com.biock.cms.site.SiteRepository;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Modification implements ValueObject<Modification> {

    private final OffsetDateTime created;
    private final String createdBy;
    private final OffsetDateTime lastModified;
    private final String lastModifiedBy;
    private String createdFormatted;
    private String lastModifiedFormatted;

    public Modification(
            final OffsetDateTime created,
            final String createdBy,
            final OffsetDateTime lastModified,
            final String lastModifiedBy) {

        this.created = created;
        this.createdBy = createdBy;
        this.lastModified = lastModified;
        this.lastModifiedBy = lastModifiedBy;
    }

    public static ModificationBuilder builder() {

        return new ModificationBuilder();
    }

    public static Modification now(final String user) {

        return new Modification(OffsetDateTime.now(), user, OffsetDateTime.now(), user);
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

    public String getCreatedFormatted() {

        return this.createdFormatted;
    }

    public String getLastModifiedFormatted() {

        return this.lastModifiedFormatted;
    }

    @Override
    public int compareTo(@NotNull final Modification other) {

        int c = this.created.compareTo(other.created);
        if (c != 0) {
            return c;
        }
        c = this.createdBy.compareTo(other.createdBy);
        if (c != 0) {
            return c;
        }
        c = this.lastModified.compareTo(other.lastModified);
        if (c != 0) {
            return c;
        }
        return this.lastModifiedBy.compareTo(other.lastModifiedBy);
    }

    public void formatDates(final SiteRepository siteRepository, final String siteId) {

        final DateTimeFormatter formatter = siteRepository.getDateFormatter(siteId);
        if (this.created != null) {
            this.createdFormatted = formatter.format(this.created);
        }
        if (this.lastModified != null) {
            this.lastModifiedFormatted = formatter.format(this.lastModified);
        }
    }
}
