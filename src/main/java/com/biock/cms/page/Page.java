package com.biock.cms.page;

import com.biock.cms.component.Component;
import com.biock.cms.shared.Builder;
import com.biock.cms.shared.Descriptor;
import com.biock.cms.shared.Label;
import com.biock.cms.shared.Modification;
import com.biock.cms.shared.page.PageConfig;
import com.biock.cms.shared.page.PageMetaData;
import com.biock.cms.utils.ComponentUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
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
    private final List<BreadcrumbItem> breadcrumbs;

    public Page(
            final String parentId,
            @NotNull final String id,
            @NotNull final Descriptor descriptor,
            @NotNull final Modification modification,
            final boolean active,
            @NotNull final Label title,
            @NotNull final String path,
            @NotNull final PageMetaData metaData,
            @NotNull final PageConfig config,
            @NotNull final List<Component> components) {

        this.parentId = parentId;
        this.id = id;
        this.descriptor = descriptor;
        this.modification = modification;
        this.active = active;
        this.title = title;
        this.path = path;
        this.metaData = metaData;
        this.config = config;
        this.components = components;
        this.breadcrumbs = new ArrayList<>();
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

        return this.components;
    }

    public List<BreadcrumbItem> getBreadcrumbs() {

        return this.breadcrumbs;
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
    public Label getMainNavigationTitle() {

        if (!this.getConfig().getMainNavigationTitle().isEmpty()) {
            return this.getConfig().getMainNavigationTitle();
        }
        return ComponentUtils.getHeadline(getComponents()).orElse(getTitle());
    }

    @JsonIgnore
    public Label getTopNavigationTitle() {

        if (!this.getConfig().getTopNavigationTitle().isEmpty()) {
            return this.getConfig().getTopNavigationTitle();
        }
        return ComponentUtils.getHeadline(getComponents()).orElse(getTitle());
    }

    @JsonIgnore
    public Label getFooterNavigationTitle() {

        if (!this.getConfig().getFooterNavigationTitle().isEmpty()) {
            return this.getConfig().getFooterNavigationTitle();
        }
        return ComponentUtils.getHeadline(getComponents()).orElse(getTitle());
    }

    public void buildBreadcrumbs(final String language, final PageRepository pageRepository) {

        this.breadcrumbs.clear();

        final Optional<Page> parentPage = pageRepository.getParentPage(this, true);
        if (parentPage.isPresent()) {
            parentPage.get().buildBreadcrumbs(language, pageRepository);
            this.breadcrumbs.addAll(parentPage.get().getBreadcrumbs());
        }

        this.breadcrumbs.add(new BreadcrumbItem(getMainNavigationTitle().getText(language), getHref()));
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

        public Builder parentId(final String parentId) {

            this.parentId = parentId;
            return this;
        }

        public Builder id(@NotNull final String id) {

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
                    Optional.ofNullable(this.components).orElse(emptyList()));
        }
    }
}
