package com.biock.cms.shared.page;

import com.biock.cms.admin.page.dto.AdminPageDTO;
import com.biock.cms.shared.Label;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static java.util.Collections.emptyMap;

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
    private final Label mainNavigationTitle;
    private final Label topNavigationTitle;
    private final Label footerNavigationTitle;

    public PageConfig(
            final boolean showInMainNavigation,
            final boolean showInTopNavigation,
            final boolean showInFooterNavigation,
            final boolean external,
            @NotNull final String href,
            @NotNull final String target,
            @NotNull final String iconClass,
            @NotNull final Label mainNavigationTitle,
            @NotNull final Label topNavigationTitle,
            @NotNull final Label footerNavigationTitle) {

        this.showInMainNavigation = showInMainNavigation;
        this.showInTopNavigation = showInTopNavigation;
        this.showInFooterNavigation = showInFooterNavigation;
        this.external = external;
        this.href = href;
        this.target = target;
        this.iconClass = iconClass;
        this.mainNavigationTitle = mainNavigationTitle;
        this.topNavigationTitle = topNavigationTitle;
        this.footerNavigationTitle = footerNavigationTitle;
    }

    public static Builder builder() {

        return new Builder();
    }

    public static PageConfig of(@NotNull final AdminPageDTO dto) {

        return PageConfig.builder()
                .showInMainNavigation(dto.isShowInMainNavigation())
                .showInTopNavigation(dto.isShowInTopNavigation())
                .showInFooterNavigation(dto.isShowInFooterNavigation())
                .external(dto.isExternal())
                .href(StringUtils.defaultString(dto.getHref()))
                .target(StringUtils.defaultString(dto.getTarget()))
                .iconClass(StringUtils.defaultString(dto.getIconClass()))
                .mainNavigationTitle(new Label(Optional.ofNullable(dto.getMainNavigationTitle()).orElse(emptyMap())))
                .topNavigationTitle(new Label(Optional.ofNullable(dto.getTopNavigationTitle()).orElse(emptyMap())))
                .footerNavigationTitle(new Label(Optional.ofNullable(dto.getFooterNavigationTitle()).orElse(emptyMap())))
                .build();
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

    public boolean hasMainNavigationTitle() {

        return !this.mainNavigationTitle.isEmpty();
    }

    public Label getMainNavigationTitle() {

        return this.mainNavigationTitle;
    }

    public boolean hasTopNavigationTitle() {

        return !this.topNavigationTitle.isEmpty();
    }

    public Label getTopNavigationTitle() {

        return this.topNavigationTitle;
    }

    public boolean hasFooterNavigationTitle() {

        return !this.footerNavigationTitle.isEmpty();
    }

    public Label getFooterNavigationTitle() {

        return this.footerNavigationTitle;
    }

    public static final class Builder implements com.biock.cms.shared.Builder<PageConfig> {

        private boolean showInMainNavigation;
        private boolean showInTopNavigation;
        private boolean showInFooterNavigation;
        private boolean external;
        private String href;
        private String target;
        private String iconClass;
        private Label mainNavigationTitle;
        private Label topNavigationTitle;
        private Label footerNavigationTitle;

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

        public Builder mainNavigationTitle(@NotNull final Label mainNavigationTitle) {

            this.mainNavigationTitle = mainNavigationTitle;
            return this;
        }

        public Builder topNavigationTitle(@NotNull final Label topNavigationTitle) {

            this.topNavigationTitle = topNavigationTitle;
            return this;
        }

        public Builder footerNavigationTitle(@NotNull final Label footerNavigationTitle) {

            this.footerNavigationTitle = footerNavigationTitle;
            return this;
        }

        @Override
        public PageConfig build() {

            return new PageConfig(
                    this.showInMainNavigation,
                    this.showInTopNavigation,
                    this.showInFooterNavigation,
                    this.external,
                    StringUtils.defaultString(this.href),
                    StringUtils.defaultString(this.target),
                    StringUtils.defaultString(this.iconClass),
                    this.mainNavigationTitle,
                    this.topNavigationTitle,
                    this.footerNavigationTitle);
        }
    }
}
