package com.biock.cms.component.mapper;

import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;
import com.biock.cms.component.Component;
import com.biock.cms.component.builder.ComponentsBuilder;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.mapper.Mapper;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.List;
import java.util.Map;

import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@org.springframework.stereotype.Component
public class ComponentsMapper implements Mapper<List<Component>> {

    private final ComponentMapper<? extends Component> defaultMapper;
    private final Map<String, ComponentMapper<? extends Component>> mappers;

    public ComponentsMapper(
            final ComponentMapper<? extends Component> defaultMapper,
            final Map<String, ComponentMapper<? extends Component>> mappers) {

        this.defaultMapper = defaultMapper;
        this.mappers = mappers;
    }

    @Override
    public ComponentsBuilder toEntityBuilder(final Node node) {

        try {
            final ComponentsBuilder builder = new ComponentsBuilder();
            final NodeIterator nodes = node.getNodes();
            while (nodes.hasNext()) {
                final Node componentNode = nodes.nextNode();
                if (CmsType.COMPONENT.isNodeType(componentNode) && componentNode.hasProperty(CmsProperty.COMPONENT_NAME)) {
                    final String componentName = getStringProperty(componentNode, CmsProperty.COMPONENT_NAME);
                    builder.component(this.mappers.getOrDefault(componentName, this.defaultMapper).toEntity(componentNode));
                }
            }
            return builder;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final List<Component> entity, final Node node) {

    }
}
