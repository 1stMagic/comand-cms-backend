package com.biock.cms.shared;

import com.biock.cms.shared.dto.DescriptorDTO;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;

public class Descriptor implements ValueObject<Descriptor> {

    private final String name;
    private final String title;
    private final String description;

    public Descriptor(@NotNull final String name, @NotNull final String title, @NotNull final String description) {

        this.name = name;
        this.title = title;
        this.description = description;
    }

    public static Builder builder() {

        return new Builder();
    }

    public static Descriptor of(@NotNull final DescriptorDTO dto) {

        return Descriptor.builder()
                .name(dto.getName())
                .title(StringUtils.defaultIfBlank(dto.getTitle(), dto.getName()))
                .description(StringUtils.defaultString(dto.getDescription()))
                .build();
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

    @Override
    public int compareTo(@NotNull final Descriptor descriptor) {

        int c = this.getName().compareTo(descriptor.getName());
        if (c != 0) {
            return c;
        }
        c = this.getTitle().compareTo(descriptor.getDescription());
        if (c != 0) {
            return c;
        }
        return this.getDescription().compareTo(descriptor.getDescription());
    }

    public static final class Builder implements com.biock.cms.shared.Builder<Descriptor> {

        private String name;
        private String title;
        private String description;

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

        @Override
        public Descriptor build() {

            return new Descriptor(this.name, this.title, this.description);
        }
    }
}
