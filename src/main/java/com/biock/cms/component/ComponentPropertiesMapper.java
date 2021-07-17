package com.biock.cms.component;

import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.Builder;
import com.biock.cms.shared.LabelMapper;
import com.biock.cms.shared.Mapper;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class ComponentPropertiesMapper implements Mapper<Map<String, ComponentProperty>> {

    @Override
    public Builder<Map<String, ComponentProperty>> toEntityBuilder(final Node node) {

        final Map<String, ComponentProperty> properties = new HashMap<>();
        final var propertyMapper = new ComponentPropertyMapper();
        processProperties(node, propertyMapper, properties);
        processNodes(node, propertyMapper, properties);
        return () -> properties;
    }

    @Override
    public void toNode(final Map<String, ComponentProperty> entity, final Node node) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void processProperties(
            final Node node,
            final ComponentPropertyMapper propertyMapper,
            final Map<String, ComponentProperty> properties) {

        try {
            final var pattern = Pattern.compile("^(.+?)_[a-z]{2}$");
            final Set<String> prefixes = new HashSet<>();
            final var propertyIterator = node.getProperties();
            while (propertyIterator.hasNext()) {
                final var property = propertyIterator.nextProperty();
                final var matcher = pattern.matcher(property.getName());
                if (matcher.matches()) {
                    prefixes.add(matcher.group(1));
                } else {
                    final var componentProperty = propertyMapper.toEntity(property);
                    if (componentProperty.getValue() != null) {
                        properties.put(componentProperty.getName(), componentProperty);
                    }
                }
            }
            processLabels(node, prefixes, properties);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private void processLabels(
            final Node node,
            final Set<String> prefixes,
            final Map<String, ComponentProperty> properties) {

        prefixes.forEach(prefix -> properties.put(
                prefix,
                ComponentProperty.builder().name(prefix).value(new LabelMapper(prefix).toEntity(node)).build()));
    }

    private void processNodes(
            final Node node,
            final ComponentPropertyMapper propertyMapper,
            final Map<String, ComponentProperty> properties) {

        try {
            final var nodeIterator = node.getNodes();
            while (nodeIterator.hasNext()) {
                final var componentProperty = propertyMapper.toEntity(nodeIterator.nextNode());
                if (componentProperty.getValue() != null) {
                    properties.put(componentProperty.getName(), componentProperty);
                }
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
