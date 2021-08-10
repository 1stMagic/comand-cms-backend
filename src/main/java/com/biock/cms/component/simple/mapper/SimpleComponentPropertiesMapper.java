package com.biock.cms.component.simple.mapper;

import com.biock.cms.component.simple.SimpleComponentProperty;
import com.biock.cms.component.simple.builder.SimpleComponentPropertiesBuilder;
import com.biock.cms.i18n.mapper.TranslationMapper;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.mapper.Mapper;

import javax.jcr.*;
import java.util.List;

import static java.util.Collections.emptyList;

@org.springframework.stereotype.Component
public class SimpleComponentPropertiesMapper implements Mapper<List<SimpleComponentProperty>> {

    private final TranslationMapper translationMapper;

    public SimpleComponentPropertiesMapper(final TranslationMapper translationMapper) {

        this.translationMapper = translationMapper;
    }

    @Override
    public SimpleComponentPropertiesBuilder toEntityBuilder(final Node node) {

        try {
            final SimpleComponentPropertiesBuilder builder = new SimpleComponentPropertiesBuilder();
            final PropertyIterator properties = node.getProperties();
            while (properties.hasNext()) {
                final Property property = properties.nextProperty();
                if (property.getType() != PropertyType.NAME) {
                    builder.property(SimpleComponentProperty.of(property));
                }
            }
            final NodeIterator translationProperties = node.getNodes();
            while (translationProperties.hasNext()) {
                final Node translationProperty = translationProperties.nextNode();
                builder.property(new SimpleComponentProperty(
                        translationProperty.getName(),
                        this.translationMapper.toEntity(translationProperty)));
            }

            return builder;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final List<SimpleComponentProperty> entity, final Node node) {

    }

    public List<SimpleComponentProperty> map(final Node node, final String nodeName) {

        try {
            if (node.hasNode(nodeName)) {
                return toEntity(node.getNode(nodeName));
            }
            return emptyList();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
