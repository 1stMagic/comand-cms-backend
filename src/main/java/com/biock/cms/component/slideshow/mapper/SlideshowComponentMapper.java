package com.biock.cms.component.slideshow.mapper;

import com.biock.cms.CmsProperty;
import com.biock.cms.component.mapper.ComponentMapper;
import com.biock.cms.component.slideshow.SlideshowComponent;
import com.biock.cms.component.slideshow.builder.SlideshowComponentBuilder;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component("CmdSlideshow")
public class SlideshowComponentMapper implements ComponentMapper<SlideshowComponent> {

    private final SlideshowItemMapper slideshowItemMapper;

    public SlideshowComponentMapper(final SlideshowItemMapper slideshowItemMapper) {

        this.slideshowItemMapper = slideshowItemMapper;
    }

    @Override
    public SlideshowComponentBuilder toEntityBuilder(final Node node) {

        try {
            return SlideshowComponent.builder()
                    .id(node.getName())
                    .active(getBooleanProperty(node, CmsProperty.ACTIVE, false))
                    .componentName(getStringProperty(node, CmsProperty.COMPONENT_NAME))
                    .items(this.slideshowItemMapper.map(node));
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final SlideshowComponent entity, final Node node) {

    }
}
