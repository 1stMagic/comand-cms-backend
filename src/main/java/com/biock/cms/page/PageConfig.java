package com.biock.cms.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;

public class PageConfig {

    private final boolean showInMainNavigation;
    private final boolean showInTopNavigation;
    private final boolean showInFooterNavigation;
    private final boolean external;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String href;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String target;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String iconClass;
    private final boolean showSlideshow;

    public PageConfig(
            final boolean showInMainNavigation,
            final boolean showInTopNavigation,
            final boolean showInFooterNavigation,
            final boolean external,
            @NotNull final String href,
            @NotNull final String target,
            @NotNull final String iconClass,
            final boolean showSlideshow) {

        this.showInMainNavigation = showInMainNavigation;
        this.showInTopNavigation = showInTopNavigation;
        this.showInFooterNavigation = showInFooterNavigation;
        this.external = external;
        this.href = href;
        this.target = target;
        this.iconClass = iconClass;
        this.showSlideshow = showSlideshow;
    }

    public static Builder builder() {

        return new Builder();
    }

    public boolean isShowInMainNavigation() {

        return this.showInMainNavigation;
    }

    public boolean isShowInTopNavigation() {

        return this.showInTopNavigation;
    }

    public boolean isShowInFooterNavigation() {

        return this.showInFooterNavigation;
    }

    public boolean isExternal() {

        return this.external;
    }

    public String getHref() {

        return this.href;
    }

    public String getTarget() {

        return this.target;
    }

    public String getIconClass() {

        return this.iconClass;
    }

    public boolean isShowSlideshow() {

        return this.showSlideshow;
    }

    public static final class Builder {

        private boolean showInMainNavigation;
        private boolean showInTopNavigation;
        private boolean showInFooterNavigation;
        private boolean external;
        private String href;
        private String target;
        private String iconClass;
        private boolean showSlideshow;

        public Builder showInMainNavigation(final boolean showInMainNavigation) {

            this.showInMainNavigation = showInMainNavigation;
            return this;
        }

        public Builder showInTopNavigation(final boolean showInTopNavigation) {

            this.showInTopNavigation = showInTopNavigation;
            return this;
        }

        public Builder showInFooterNavigation(final boolean showInFooterNavigation) {

            this.showInFooterNavigation = showInFooterNavigation;
            return this;
        }

        public Builder external(final boolean external) {

            this.external = external;
            return this;
        }

        public Builder href(@NotNull final String href) {

            this.href = href;
            return this;
        }

        public Builder target(@NotNull final String target) {

            this.target = target;
            return this;
        }

        public Builder iconClass(@NotNull final String iconClass) {

            this.iconClass = iconClass;
            return this;
        }

        public Builder showSlideshow(final boolean showSlideshow) {

            this.showSlideshow = showSlideshow;
            return this;
        }

        public PageConfig build() {

            return new PageConfig(
                    this.showInMainNavigation,
                    this.showInTopNavigation,
                    this.showInFooterNavigation,
                    this.external,
                    StringUtils.defaultString(this.href),
                    StringUtils.defaultString(this.target),
                    StringUtils.defaultString(this.iconClass),
                    this.showSlideshow);
        }
    }
}
