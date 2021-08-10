package com.biock.cms.component.slideshow.builder;

import com.biock.cms.component.slideshow.SlideshowImage;
import com.biock.cms.shared.builder.Builder;

public class SlideshowImageBuilder implements Builder<SlideshowImage> {

    private String relativeImagePath;
    private int maxWidth;

    public SlideshowImageBuilder relativeImagePath(final String relativeImagePath) {

        this.relativeImagePath = relativeImagePath;
        return this;
    }

    public SlideshowImageBuilder maxWidth(final int maxWidth) {

        this.maxWidth = maxWidth;
        return this;
    }

    @Override
    public SlideshowImageBuilder apply(final SlideshowImage other) {

        if (other != null) {
            return relativeImagePath(other.getRelativeImagePath())
                    .maxWidth(other.getMaxWidth());
        }
        return this;
    }

    @Override
    public SlideshowImage build() {

        return new SlideshowImage(this.relativeImagePath, this.maxWidth);
    }
}
