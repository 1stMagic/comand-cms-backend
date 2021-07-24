package com.biock.cms.admin.page;

import com.biock.cms.admin.page.dto.AdminPageDTO;
import com.biock.cms.shared.Descriptor;
import com.biock.cms.shared.Label;
import com.biock.cms.shared.Modification;
import com.biock.cms.shared.page.PageConfig;
import com.biock.cms.shared.page.PageMetaData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public class AdminPage {

    private final String parentId;
    private final String id;
    private final Descriptor descriptor;
    private final Modification modification;
    private final boolean active;
    private final Label title;
    private final String path;
    @JsonUnwrapped
    private final PageMetaData metaData;
    private final PageConfig config;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<AdminPage> children;

    public AdminPage(
            final String parentId,
            final String id,
            @NotNull final Descriptor descriptor,
            @NotNull final Modification modification,
            final boolean active,
            @NotNull final Label title,
            final String path,
            @NotNull final PageMetaData metaData,
            @NotNull final PageConfig config,
            @NotNull final List<AdminPage> children) {

        this.parentId = parentId;
        this.id = id;
        this.descriptor = descriptor;
        this.modification = modification;
        this.active = active;
        this.title = title;
        this.path = path;
        this.metaData = metaData;
        this.config = config;
        this.children = children;
    }

    public static Builder builder() {
        
        return new Builder();
    }

    public static Builder builder(@NotNull final AdminPage page) {

        return new Builder()
                .parentId(page.getParentId())
                .id(page.getId())
                .descriptor(page.getDescriptor())
                .modification(page.getModification())
                .active(page.isActive())
                .title(page.getTitle())
                .path(page.getPath())
                .metaData(page.getMetaData())
                .config(page.getConfig())
                .children(page.getChildren());
    }

    public static AdminPage of(@NotNull final AdminPageDTO dto) {

        return AdminPage.builder()
                .parentId(dto.getParentId())
                .descriptor(Descriptor.of(dto.getDescriptor()))
                .modification(Modification.of("api", OffsetDateTime.now()))
                .active(dto.isActive())
                .title(new Label(dto.getTitle()))
                .metaData(new PageMetaData(Optional.ofNullable(dto.getMetaData()).orElse(emptyMap())))
                .config(PageConfig.of(dto))
                .build();
    }

    public String getParentId() {

        return this.parentId;
    }

    public String getId() {

        return this.id;
    }

    public Descriptor getDescriptor() {

        return this.descriptor;
    }

    public Modification getModification() {

        return this.modification;
    }

    public boolean isActive() {

        return this.active;
    }

    public Label getTitle() {

        return this.title;
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

    public List<AdminPage> getChildren() {

        return this.children;
    }

    @JsonIgnore
    public String getTopNavigationTitle(@NotNull final String language) {

        return StringUtils.defaultIfBlank(
                StringUtils.defaultIfBlank(
                    getConfig().getTopNavigationTitle().getText(language),
                    getTitle().getText(language)),
                getDescriptor().getTitle());
    }

    @JsonIgnore
    public String getMainNavigationTitle(@NotNull final String language) {

        return StringUtils.defaultIfBlank(
                StringUtils.defaultIfBlank(
                    getConfig().getMainNavigationTitle().getText(language),
                    getTitle().getText(language)),
                getDescriptor().getTitle());
    }

    @JsonIgnore
    public String getFooterNavigationTitle(@NotNull final String language) {

        return StringUtils.defaultIfBlank(
                StringUtils.defaultIfBlank(
                    getConfig().getFooterNavigationTitle().getText(language),
                    getTitle().getText(language)),
                getDescriptor().getTitle());
    }

    public static final class Builder implements com.biock.cms.shared.Builder<AdminPage> {

        private String parentId;
        private String id;
        private Descriptor descriptor;
        private Modification modification;
        private boolean active;
        private Label title;
        private String path;
        private PageMetaData metaData;
        private PageConfig config;
        private List<AdminPage> children;

        public Descriptor descriptor() {

            return this.descriptor;
        }

        public String path() {

            return this.path;
        }

        public Builder parentId(final String parentId) {

            this.parentId = parentId;
            return this;
        }

        public Builder id(final String id) {

            this.id = id;
            return this;
        }

        public Builder descriptor(@NotNull final Descriptor descriptor) {

            this.descriptor = descriptor;
            return this;
        }

        public Builder modification(@NotNull final Modification modification) {

            this.modification = modification;
            return this;
        }

        public Builder active(final boolean active) {

            this.active = active;
            return this;
        }

        public Builder title(final Label title) {

            this.title = title;
            return this;
        }

        public Builder path(final String path) {

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

        public Builder children(@NotNull final List<AdminPage> children) {

            this.children = children;
            return this;
        }

        @Override
        public AdminPage build() {

            return new AdminPage(
                    this.parentId,
                    this.id,
                    this.descriptor,
                    this.modification,
                    this.active,
                    this.title,
                    this.path,
                    Optional.ofNullable(this.metaData).orElse(new PageMetaData(emptyMap())),
                    this.config,
                    Optional.ofNullable(this.children).orElse(emptyList()));
        }
    }
}
