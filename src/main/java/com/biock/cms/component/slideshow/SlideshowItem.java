package com.biock.cms.component.slideshow;

import com.biock.cms.component.slideshow.builder.SlideshowItemBuilder;

import java.util.List;

public class SlideshowItem {

    private final List<SlideshowImage> images;
    private final String alt;
    private final String title;
    private final String figCaption;
    private final String href;
    private final String target;

    public SlideshowItem(
            final List<SlideshowImage> images,
            final String alt,
            final String title,
            final String figCaption,
            final String href,
            final String target) {

        this.images = images;
        this.alt = alt;
        this.title = title;
        this.figCaption = figCaption;
        this.href = href;
        this.target = target;
    }

    public static SlideshowItemBuilder builder() {

        return new SlideshowItemBuilder();
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
}
