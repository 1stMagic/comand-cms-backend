package com.biock.cms.component.slideshow.mapper;

import com.biock.cms.component.slideshow.SlideshowImage;
import com.biock.cms.component.slideshow.builder.SlideshowImageBuilder;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.mapper.Mapper;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

import static com.biock.cms.jcr.PropertyUtils.getIntegerProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component
public class SlideshowImageMapper implements Mapper<SlideshowImage> {

    private static final String PROPERTY_RELATIVE_IMAGE_PATH = "relativeImagePath";
    private static final String PROPERTY_MAX_WIDTH = "maxWidth";

    @Override
    public SlideshowImageBuilder toEntityBuilder(final Node node) {

        return SlideshowImage.builder()
                .relativeImagePath(getStringProperty(node, PROPERTY_RELATIVE_IMAGE_PATH))
                .maxWidth(getIntegerProperty(node, PROPERTY_MAX_WIDTH, 0));
    }

    @Override
    public void toNode(final SlideshowImage entity, final Node node) {

    }

    public List<SlideshowImage> map(final Node node) {

        try {
            final List<SlideshowImage> images = new ArrayList<>();
            final NodeIterator imageNodes = node.getNodes();
            while (imageNodes.hasNext()) {
                images.add(toEntity(imageNodes.nextNode()));
            }
            return images;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
