package com.biock.cms.component.simple.mapper;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.component.mapper.ComponentMapper;
import com.biock.cms.component.simple.SimpleComponent;
import com.biock.cms.component.simple.builder.SimpleComponentBuilder;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import org.springframework.context.annotation.Primary;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Primary
@org.springframework.stereotype.Component
public class SimpleComponentMapper implements ComponentMapper<SimpleComponent> {

    private final SimpleComponentPropertiesMapper componentPropertiesMapper;

    public SimpleComponentMapper(final SimpleComponentPropertiesMapper componentPropertiesMapper) {

        this.componentPropertiesMapper = componentPropertiesMapper;
    }

    @Override
    public SimpleComponentBuilder toEntityBuilder(final Node node) {

        try {
            return SimpleComponent.builder()
                    .id(node.getName())
                    .active(getBooleanProperty(node, CmsProperty.ACTIVE, false))
                    .componentName(getStringProperty(node, CmsProperty.COMPONENT_NAME))
                    .properties(this.componentPropertiesMapper.map(node, CmsNode.PROPERTIES))
                    .components(new SimpleComponentsMapper(this).toEntity(node));
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final SimpleComponent entity, final Node node) {

    }
}
