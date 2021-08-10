package com.biock.cms.component.slideshow.dto.frontend;

import com.biock.cms.component.slideshow.SlideshowImage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SlideshowImageDTO {

    @JsonProperty("imgPath")
    private String relativeImagePath;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int maxWidth;

    public static SlideshowImageDTO of(final SlideshowImage entity) {

        if (entity == null) {
            return new SlideshowImageDTO();
        }
        return new SlideshowImageDTO()
                .setRelativeImagePath(entity.getRelativeImagePath())
                .setMaxWidth(entity.getMaxWidth());
    }

    public String getRelativeImagePath() {

        return this.relativeImagePath;
    }

    public SlideshowImageDTO setRelativeImagePath(final String relativeImagePath) {

        this.relativeImagePath = relativeImagePath;
        return this;
    }

    public int getMaxWidth() {

        return this.maxWidth;
    }

    public SlideshowImageDTO setMaxWidth(final int maxWidth) {

        this.maxWidth = maxWidth;
        return this;
    }
}
