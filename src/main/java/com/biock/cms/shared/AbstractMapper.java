package com.biock.cms.shared;

import com.biock.cms.CmsType;
import com.biock.cms.component.Component;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

public abstract class AbstractMapper<T> implements Mapper<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractMapper.class);

    protected List<Component> getComponents(final Map<String, Mapper<? extends Component>> mappers, final Node pageNode) {

        try {
            final List<com.biock.cms.component.Component> components = new ArrayList<>();
            final NodeIterator nodes = pageNode.getNodes();
            while (nodes.hasNext()) {
                final var node = nodes.nextNode();
                if (node.getPrimaryNodeType().isNodeType(CmsType.COMPONENT)) {
                    final var componentName = getStringProperty(node, Property.JCR_NAME);
                    final String mapperBeanName = componentName + "_mapper";
                    if (!mappers.containsKey(mapperBeanName)) {
                        LOG.error("No mapper bean found for component '{}' - expected mapper bean name: '{}'", componentName, mapperBeanName);
                        continue;
                    }
                    components.add(mappers.get(mapperBeanName).toEntity(node));
                }
            }
            return components;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
