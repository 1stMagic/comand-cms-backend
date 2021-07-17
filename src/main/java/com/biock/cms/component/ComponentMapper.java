package com.biock.cms.component;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.AbstractMapper;
import com.biock.cms.shared.Mapper;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;
import java.util.Map;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;
import static java.util.Collections.emptyMap;

@org.springframework.stereotype.Component
public class ComponentMapper extends AbstractMapper<Component> {

    private final Map<String, Mapper<? extends Component>> mappers;

    public ComponentMapper(final Map<String, Mapper<? extends Component>> mappers) {

        this.mappers = mappers;
    }

    @Override
    public Component.Builder toEntityBuilder(@NotNull final Node node) {

        return Component.builder()
                .name(getStringProperty(node, Property.JCR_NAME))
                .active(getBooleanProperty(node, CmsProperty.ACTIVE))
                .componentName(getStringProperty(node, CmsProperty.COMPONENT_NAME))
                .properties(getProperties(node))
                .components(getComponents(this, this.mappers, node));
    }

    @Override
    public void toNode(@NotNull final Component entity, @NotNull final Node node) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Map<String, ComponentProperty> getProperties(@NotNull final Node node) {

        try {
            if (node.hasNode(CmsNode.PROPERTIES)) {
                return new ComponentPropertiesMapper().toEntity(node.getNode(CmsNode.PROPERTIES));
            }
            return emptyMap();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
