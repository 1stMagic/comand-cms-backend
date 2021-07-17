package com.biock.cms.component.slideshow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SlideshowItem {

    private final List<SlideshowImage> images;
    private final String alt;
    private final String title;
    @JsonProperty("figcaption")
    private final String figCaption;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String href;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String target;

    public SlideshowItem(
            @NotNull final List<SlideshowImage> images,
            @NotNull final String alt,
            @NotNull final String title,
            @NotNull final String figCaption,
            @NotNull final String href,
            @NotNull final String target) {

        this.images = images;
        this.alt = alt;
        this.title = title;
        this.figCaption = figCaption;
        this.href = href;
        this.target = target;
    }

    public static Builder builder() {

        return new Builder();
    }

    public List<SlideshowImage> getImages() {

        return this.images;
    }

    public String getAlt() {

        return this.alt;
    }

    public String getTitle() {

        return this.title;
    }

    public String getFigCaption() {

        return this.figCaption;
    }

    public String getHref() {

        return this.href;
    }

    public String getTarget() {

        return this.target;
    }

    public static final class Builder implements com.biock.cms.shared.Builder<SlideshowItem> {

        private List<SlideshowImage> images;
        private String alt;
        private String title;
        private String figCaption;
        private String href;
        private String target;

        public Builder images(@NotNull final List<SlideshowImage> images) {

            this.images = images;
            return this;
        }

        public Builder alt(@NotNull final String alt) {

            this.alt = alt;
            return this;
        }

        public Builder title(@NotNull final String title) {

            this.title = title;
            return this;
        }

        public Builder figCaption(@NotNull final String figCaption) {

            this.figCaption = figCaption;
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

        @Override
        public SlideshowItem build() {

            return new SlideshowItem(this.images, this.alt, this.title, this.figCaption, this.href, this.target);
        }
    }
}
