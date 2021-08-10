package com.biock.cms.component.slideshow;

import com.biock.cms.component.slideshow.builder.SlideshowImageBuilder;

public class SlideshowImage {

    private final String relativeImagePath;
    private final int maxWidth;

    public SlideshowImage(final String relativeImagePath, final int maxWidth) {

        this.relativeImagePath = relativeImagePath;
        this.maxWidth = maxWidth;
    }

    public static SlideshowImageBuilder builder() {

        return new SlideshowImageBuilder();
    }

    public String getRelativeImagePath() {

        return this.relativeImagePath;
    }

    public int getMaxWidth() {

        return this.maxWidth;
    }
}
