package com.biock.cms.component.slideshow;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.Mapper;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

import static com.biock.cms.jcr.PropertyUtils.*;

@Component("slideshow_mapper")
public class SlideshowMapper implements Mapper<SlideshowComponent> {

    private static final String PROPERTY_ALT = "alt";
    private static final String PROPERTY_TITLE = "title";
    private static final String PROPERTY_FIG_CAPTION = "figCaption";
    private static final String PROPERTY_HREF = "href";
    private static final String PROPERTY_TARGET = "target";

    private static final String PROPERTY_RELATIVE_IMAGE_PATH = "relativeImagePath";
    private static final String PROPERTY_MAX_WIDTH = "maxWidth";

    private static final String NODE_ITEMS = "items";

    @Override
    public SlideshowComponent.Builder toEntityBuilder(final Node node) {

        return SlideshowComponent.builderx()
                .name(getStringProperty(node, Property.JCR_NAME))
                .active(getBooleanProperty(node, CmsProperty.ACTIVE))
                .componentName(getStringProperty(node, CmsProperty.COMPONENT_NAME))
                .items(getItems(node));
    }

    @Override
    public void toNode(final SlideshowComponent entity, final Node node) {

        throw new UnsupportedOperationException();
    }

    private List<SlideshowItem> getItems(final Node slideshowComponentNode) {

        try {
            final List<SlideshowItem> items = new ArrayList<>();
            if (slideshowComponentNode.hasNode(JcrPaths.relative(CmsNode.PROPERTIES, NODE_ITEMS))) {
                final NodeIterator slideshowItemNodes = slideshowComponentNode.getNode(JcrPaths.relative(CmsNode.PROPERTIES, NODE_ITEMS)).getNodes();
                while (slideshowItemNodes.hasNext()) {
                    final var slideshowItemNode = slideshowItemNodes.nextNode();
                    items.add(SlideshowItem.builder()
                            .alt(getStringProperty(slideshowItemNode, PROPERTY_ALT))
                            .title(getStringProperty(slideshowItemNode, PROPERTY_TITLE))
                            .figCaption(getStringProperty(slideshowItemNode, PROPERTY_FIG_CAPTION))
                            .href(getStringProperty(slideshowItemNode, PROPERTY_HREF))
                            .target(getStringProperty(slideshowItemNode, PROPERTY_TARGET))
                            .images(getImages(slideshowItemNode))
                            .build()
                    );
                }
            }
            return items;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private List<SlideshowImage> getImages(final Node slideshowItemNode) {

        try {
            final List<SlideshowImage> images = new ArrayList<>();
            final NodeIterator slideshowImageNodes = slideshowItemNode.getNodes();
            while (slideshowImageNodes.hasNext()) {
                final var slideshowImageNode = slideshowImageNodes.nextNode();
                images.add(SlideshowImage.builder()
                        .relativeImagePath(getStringProperty(slideshowImageNode, PROPERTY_RELATIVE_IMAGE_PATH))
                        .maxWidth(getIntegerProperty(slideshowImageNode, PROPERTY_MAX_WIDTH, 0))
                        .build()
                );
            }
            return images;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
