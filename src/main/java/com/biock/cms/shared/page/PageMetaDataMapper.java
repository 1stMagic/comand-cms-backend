package com.biock.cms.shared.page;

import com.biock.cms.CmsNode;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.Mapper;

import javax.jcr.Node;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;

public class PageMetaDataMapper implements Mapper<PageMetaData> {

    public PageMetaData.Builder toEntityBuilder(@NotNull final Node node) {

        try {
            final var builder = PageMetaData.builder();
            final PropertyIterator properties = node.getProperties();
            while (properties.hasNext()) {
                final var property = properties.nextProperty();
                if (!property.isMultiple() && property.getType() == PropertyType.STRING) {
                    builder.put(property.getName(), property.getString());
                }
            }
            return builder;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public void toNode(@NotNull final PageMetaData metaData, @NotNull final Node node) {

        metaData.forEach(entry -> {
            try {
                node.setProperty(entry.getKey(), entry.getValue());
            } catch (final RepositoryException e) {
                throw new RuntimeRepositoryException(e);
            }
        });
    }
}
