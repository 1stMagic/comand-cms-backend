package com.biock.cms.component.slideshow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class SlideshowImage {

    @JsonProperty("imgPath")
    private final String relativeImagePath;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private final int maxWidth;

    public SlideshowImage(@NotNull final String relativeImagePath, final int maxWidth) {

        this.relativeImagePath = relativeImagePath;
        this.maxWidth = maxWidth;
    }

    public static Builder builder() {

        return new Builder();
    }

    public String getRelativeImagePath() {

        return this.relativeImagePath;
    }

    public int getMaxWidth() {

        return this.maxWidth;
    }

    public static final class Builder implements com.biock.cms.shared.Builder<SlideshowImage> {

        private String relativeImagePath;
        private int maxWidth;

        public Builder relativeImagePath(@NotNull final String relativeImagePath) {

            this.relativeImagePath = relativeImagePath;
            return this;
        }

        public Builder maxWidth(final int maxWidth) {

            this.maxWidth = maxWidth;
            return this;
        }

        @Override
        public SlideshowImage build() {

            return new SlideshowImage(this.relativeImagePath, this.maxWidth);
        }
    }
}
