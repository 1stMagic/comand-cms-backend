package com.biock.cms.component.slideshow.builder;

import com.biock.cms.component.slideshow.SlideshowImage;
import com.biock.cms.component.slideshow.SlideshowItem;
import com.biock.cms.shared.builder.Builder;

import java.util.List;

public class SlideshowItemBuilder implements Builder<SlideshowItem> {

    private List<SlideshowImage> images;
    private String alt;
    private String title;
    private String figCaption;
    private String href;
    private String target;

    public SlideshowItemBuilder images(final List<SlideshowImage> images) {

        this.images = images;
        return this;
    }

    public SlideshowItemBuilder alt(final String alt) {

        this.alt = alt;
        return this;
    }

    public SlideshowItemBuilder title(final String title) {

        this.title = title;
        return this;
    }

    public SlideshowItemBuilder figCaption(final String figCaption) {

        this.figCaption = figCaption;
        return this;
    }

    public SlideshowItemBuilder href(final String href) {

        this.href = href;
        return this;
    }

    public SlideshowItemBuilder target(final String target) {

        this.target = target;
        return this;
    }

    @Override
    public SlideshowItemBuilder apply(final SlideshowItem other) {

        if (other != null) {
            return images(other.getImages())
                    .alt(other.getAlt())
                    .title(other.getTitle())
                    .figCaption(other.getFigCaption())
                    .href(other.getHref())
                    .target(other.getTarget());
        }
        return this;
    }

    @Override
    public SlideshowItem build() {

        return new SlideshowItem(
                this.images,
                this.alt,
                this.title,
                this.figCaption,
                this.href,
                this.target);
    }
}
