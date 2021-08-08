package com.biock.cms.i18n.mapper;

import com.biock.cms.CmsType;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.i18n.Translation;
import com.biock.cms.i18n.builder.TranslationBuilder;
import com.biock.cms.shared.mapper.Mapper;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;

import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component
public class TranslationMapper implements Mapper<Translation> {

    @Override
    public TranslationBuilder toEntityBuilder(final Node node) {

        try {
            final TranslationBuilder builder = Translation.builder();
            if (CmsType.TRANSLATION.isNodeType(node)) {
                final PropertyIterator properties = node.getProperties();
                while (properties.hasNext()) {
                    final Property property = properties.nextProperty();
                    builder.translation(property.getName(), getStringProperty(property));
                }
            }
            return builder;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final Translation entity, final Node node) {

    }

    public Translation map(final Node node, final String nodeName) {

        try {
            if (node.hasNode(nodeName)) {
                return toEntity(node.getNode(nodeName));
            }
            return Translation.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
