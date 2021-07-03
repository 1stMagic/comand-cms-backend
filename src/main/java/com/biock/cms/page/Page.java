package com.biock.cms.page;

import com.biock.cms.component.Component;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public class Page {

    private final String parentId;
    @NotEmpty(message = "Please provide an id")
    private final String id;
    @NotEmpty(message = "Please provide a name")
    private final String name;
    @NotEmpty(message = "Please provide a title")
    private final String title;
    private final String description;
    @NotEmpty(message = "Please provide a createdBy")
    private final OffsetDateTime created;
    private final String createdBy;
    private final OffsetDateTime lastModified;
    private final String lastModifiedBy;
    private final boolean active;
    private final String headline;
    private final String path;
    @JsonUnwrapped
    private final PageMetaData metaData;
    private final PageConfig config;
    private final List<Component> components;

    public Page(
            final String parentId,
            @NotNull final String id,
            @NotNull final String name,
            @NotNull final String title,
            @NotNull final String description,
            @NotNull final OffsetDateTime created,
            @NotNull final String createdBy,
            @NotNull final OffsetDateTime lastModified,
            @NotNull final String lastModifiedBy,
            final boolean active,
            @NotNull final String headline,
            @NotNull final String path,
            @NotNull final PageMetaData metaData,
            @NotNull final PageConfig config,
            @NotNull final List<Component> components) {

        this.parentId = parentId;
        this.id = id;
        this.name = name;
        this.title = title;
        this.description = description;
        this.created = created;
        this.createdBy = createdBy;
        this.lastModified = lastModified;
        this.lastModifiedBy = lastModifiedBy;
        this.active = active;
        this.headline = headline;
        this.path = path;
        this.metaData = metaData;
        this.config = config;
        this.components = components;
    }

    public static Builder builder() {
        
        return new Builder();
    }

    public String getParentId() {

        return this.parentId;
    }

    public String getId() {

        return this.id;
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

    public String getHeadline() {

        return this.headline;
    }

    public String getPath() {

        return this.path;
    }

    public PageMetaData getMetaData() {

        return this.metaData;
    }

    public PageConfig getConfig() {

        return this.config;
    }

    public List<Component> getComponents() {

        return this.components;
    }

    @JsonIgnore
    public String getHref() {

        if (this.config.isExternal()) {
            return this.config.getHref();
        }
        return Stream.of(getPath().split("/"))
            .skip(4)
            .collect(Collectors.joining("/")) + ".html";
    }

    public static final class Builder {

        private String parentId;
        private String id;
        private String name;
        private String title;
        private String description;
        private OffsetDateTime created;
        private String createdBy;
        private OffsetDateTime lastModified;
        private String lastModifiedBy;
        private boolean active;
        private String headline;
        private String path;
        private PageMetaData metaData;
        private PageConfig config;
        private List<Component> components;

        public Builder parentId(final String parentId) {

            this.parentId = parentId;
            return this;
        }

        public Builder id(@NotNull final String id) {

            this.id = id;
            return this;
        }

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

        public Builder headline(final String headline) {

            this.headline = headline;
            return this;
        }

        public Builder path(@NotNull final String path) {

            this.path = path;
            return this;
        }

        public Builder metaData(@NotNull final PageMetaData metaData) {

            this.metaData = metaData;
            return this;
        }

        public Builder config(@NotNull final PageConfig config) {

            this.config = config;
            return this;
        }

        public Builder components(@NotNull final List<Component> components) {

            this.components = components;
            return this;
        }

        public Page build() {

            return new Page(
                    this.parentId,
                    this.id,
                    this.name,
                    this.title,
                    this.description,
                    this.created,
                    this.createdBy,
                    this.lastModified,
                    this.lastModifiedBy,
                    this.active,
                    this.headline,
                    this.path,
                    Optional.ofNullable(this.metaData).orElse(new PageMetaData(emptyMap())),
                    this.config,
                    Optional.ofNullable(this.components).orElse(emptyList()));
        }
    }
}
