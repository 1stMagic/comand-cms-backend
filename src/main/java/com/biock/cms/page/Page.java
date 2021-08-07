package com.biock.cms.page;

import com.biock.cms.admin.page.dto.CreatePageDTO;
import com.biock.cms.admin.page.dto.UpdatePageDTO;
import com.biock.cms.component.Component;
import com.biock.cms.shared.Descriptor;
import com.biock.cms.shared.Label;
import com.biock.cms.shared.Modification;
import com.biock.cms.shared.page.PageConfig;
import com.biock.cms.shared.page.PageMetaData;
import com.biock.cms.utils.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;

public class Page {

    private final String parentId;
    private final String id;
    private final Descriptor descriptor;
    private final Modification modification;
    private final boolean active;
    private final Label title;
    private final String path;
    private final PageMetaData metaData;
    private final PageConfig config;
    private final List<Component> components;
    private final List<Page> children;

    public Page(
            final String parentId,
            final String id,
            final Descriptor descriptor,
            final Modification modification,
            final boolean active,
            final Label title,
            final String path,
            final PageMetaData metaData,
            final PageConfig config,
            final List<Component> components,
            final List<Page> children) {

        this.parentId = parentId;
        this.id = id;
        this.descriptor = descriptor;
        this.modification = modification;
        this.active = active;
        this.title = title;
        this.path = path;
        this.metaData = metaData;
        this.config = config;
        this.components = CollectionUtils.copy(components);
        this.children = CollectionUtils.copy(children);
    }

    public static Builder builder() {
        
        return new Builder();
    }

    public static Page of(@NotNull final CreatePageDTO dto) {

        return Page.builder()
                .parentId(dto.getParentId())
                .descriptor(Descriptor.of(dto.getDescriptor()))
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

    public List<Component> getComponents() {

        return CollectionUtils.copy(this.components);
    }

    public List<Page> getChildren() {

        return CollectionUtils.copy(this.children);
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

    public Page apply(final UpdatePageDTO update) {

        return builder().apply(this).active(update.isActive()).build();
    }

    public static final class Builder implements com.biock.cms.shared.Builder<Page> {

        private String parentId;
        private String id;
        private Descriptor descriptor;
        private Modification modification;
        private boolean active;
        private Label title;
        private String path;
        private PageMetaData metaData;
        private PageConfig config;
        private List<Component> components;
        private List<Page> children;

        public Builder apply(final Page page) {

            return parentId(page.getParentId())
                    .id(page.getId())
                    .descriptor(page.getDescriptor())
                    .modification(page.getModification())
                    .active(page.isActive())
                    .title(page.getTitle())
                    .path(page.getPath())
                    .metaData(page.getMetaData())
                    .config(page.getConfig())
                    .components(page.getComponents())
                    .children(page.getChildren());
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

        public Builder descriptor(final Descriptor descriptor) {

            this.descriptor = descriptor;
            return this;
        }

        public Builder modification(final Modification modification) {

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

        public Builder metaData(final PageMetaData metaData) {

            this.metaData = metaData;
            return this;
        }

        public Builder config(final PageConfig config) {

            this.config = config;
            return this;
        }

        public Builder components(final List<Component> components) {

            this.components = components;
            return this;
        }

        public Builder children(final List<Page> children) {

            this.children = children;
            return this;
        }

        @Override
        public Page build() {

            return new Page(
                    this.parentId,
                    this.id,
                    this.descriptor,
                    this.modification,
                    this.active,
                    this.title,
                    this.path,
                    Optional.ofNullable(this.metaData).orElse(new PageMetaData(emptyMap())),
                    this.config,
                    this.components,
                    this.children);
        }
    }
}
