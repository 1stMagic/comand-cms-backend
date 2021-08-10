package com.biock.cms.component.slideshow.mapper;

import com.biock.cms.CmsNode;
import com.biock.cms.component.slideshow.SlideshowItem;
import com.biock.cms.component.slideshow.builder.SlideshowItemBuilder;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.mapper.Mapper;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component
public class SlideshowItemMapper implements Mapper<SlideshowItem> {

    private static final String NODE_ITEMS = "items";

    private static final String PROPERTY_ALT = "alt";
    private static final String PROPERTY_TITLE = "title";
    private static final String PROPERTY_FIG_CAPTION = "figCaption";
    private static final String PROPERTY_HREF = "href";
    private static final String PROPERTY_TARGET = "target";

    private final SlideshowImageMapper slideshowImageMapper;

    public SlideshowItemMapper(final SlideshowImageMapper slideshowImageMapper) {

        this.slideshowImageMapper = slideshowImageMapper;
    }

    @Override
    public SlideshowItemBuilder toEntityBuilder(final Node node) {

        return SlideshowItem.builder()
                .alt(getStringProperty(node, PROPERTY_ALT))
                .title(getStringProperty(node, PROPERTY_TITLE))
                .figCaption(getStringProperty(node, PROPERTY_FIG_CAPTION))
                .href(getStringProperty(node, PROPERTY_HREF))
                .target(getStringProperty(node, PROPERTY_TARGET))
                .images(this.slideshowImageMapper.map(node));
    }

    @Override
    public void toNode(final SlideshowItem entity, final Node node) {

    }

    public List<SlideshowItem> map(final Node node) {

        try {
            final List<SlideshowItem> items = new ArrayList<>();
            final String itemsPath = JcrPaths.relative(CmsNode.PROPERTIES, NODE_ITEMS);
            if (node.hasNode(itemsPath)) {
                final NodeIterator itemNodes = node.getNode(itemsPath).getNodes();
                while (itemNodes.hasNext()) {
                    items.add(toEntity(itemNodes.nextNode()));
                }
            }
            return items;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
