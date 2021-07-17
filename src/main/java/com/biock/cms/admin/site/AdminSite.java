package com.biock.cms.admin.site;

import com.biock.cms.shared.Descriptor;
import com.biock.cms.shared.Modification;
import com.biock.cms.site.DefaultSiteConfig;
import com.biock.cms.shared.site.SiteConfig;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class AdminSite {

    private final Descriptor descriptor;
    private final Modification modification;
    private final boolean active;
    private final SiteConfig config;

    public AdminSite(
            @NotNull final Descriptor descriptor,
            @NotNull final Modification modification,
            final boolean active,
            @NotNull final SiteConfig config) {

        this.descriptor = descriptor;
        this.modification = modification;
        this.active = active;
        this.config = config;
    }

    public static Builder builder() {

        return new Builder();
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

    public SiteConfig getConfig() {

        return this.config;
    }

    public static final class Builder implements com.biock.cms.shared.Builder<AdminSite> {

        private Descriptor descriptor;
        private Modification modification;
        private boolean active;
        private SiteConfig config;

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

        public Builder config(@NotNull final SiteConfig config) {

            this.config = config;
            return this;
        }

        @Override
        public AdminSite build() {

            return new AdminSite(
                    this.descriptor,
                    this.modification,
                    this.active,
                    Optional.ofNullable(this.config).orElse(new DefaultSiteConfig()));
        }
    }
}
