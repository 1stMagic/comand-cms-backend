package com.biock.cms.component.slideshow.dto.frontend;

import com.biock.cms.component.slideshow.SlideshowItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class SlideshowItemDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SlideshowImageDTO> images;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String alt;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String title;
    @JsonProperty("figcaption")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String figCaption;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String href;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String target;

    public static SlideshowItemDTO of(final SlideshowItem entity) {

        if (entity == null) {
            return new SlideshowItemDTO();
        }
        return new SlideshowItemDTO()
                .setImages(Optional.ofNullable(entity.getImages())
                        .orElse(emptyList())
                        .stream()
                        .map(SlideshowImageDTO::of)
                        .collect(toList()))
                .setAlt(entity.getAlt())
                .setTitle(entity.getTitle())
                .setFigCaption(entity.getFigCaption())
                .setHref(entity.getHref())
                .setTarget(entity.getTarget());
    }

    public List<SlideshowImageDTO> getImages() {

        return this.images;
    }

    public SlideshowItemDTO setImages(final List<SlideshowImageDTO> images) {

        this.images = images;
        return this;
    }

    public String getAlt() {

        return this.alt;
    }

    public SlideshowItemDTO setAlt(final String alt) {

        this.alt = alt;
        return this;
    }

    public String getTitle() {

        return this.title;
    }

    public SlideshowItemDTO setTitle(final String title) {

        this.title = title;
        return this;
    }

    public String getFigCaption() {

        return this.figCaption;
    }

    public SlideshowItemDTO setFigCaption(final String figCaption) {

        this.figCaption = figCaption;
        return this;
    }

    public String getHref() {

        return this.href;
    }

    public SlideshowItemDTO setHref(final String href) {

        this.href = href;
        return this;
    }

    public String getTarget() {

        return this.target;
    }

    public SlideshowItemDTO setTarget(final String target) {

        this.target = target;
        return this;
    }
}
