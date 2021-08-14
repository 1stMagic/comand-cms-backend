package com.biock.cms.i18n.mapper;

import com.biock.cms.CmsType;
import com.biock.cms.i18n.Translation;
import com.biock.cms.i18n.builder.TranslationBuilder;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.mapper.Mapper;
import org.springframework.stereotype.Component;

import javax.jcr.*;
import java.util.Map.Entry;

import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component
public class TranslationMapper implements Mapper<Translation> {

    @Override
    public TranslationBuilder toEntityBuilder(final Node node) {

        try {
            final TranslationBuilder builder = Translation.builder();
            final PropertyIterator properties = node.getProperties();
            while (properties.hasNext()) {
                final Property property = properties.nextProperty();
                if (property.getType() == PropertyType.STRING) {
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

        try {
            for (final Entry<String, String> translation : entity.getTranslations().entrySet()) {
                node.setProperty(translation.getKey(), translation.getValue());
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
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

    public void map(final Translation entity, final Node node, final String nodeName) {

        try {
            if (entity != null) {
                toNode(
                        entity,
                        node.hasNode(nodeName)
                                ? node.getNode(nodeName)
                                : node.addNode(nodeName, CmsType.TRANSLATION.getName()));
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
