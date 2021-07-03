package com.biock.cms.page;

import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.Mapper;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;

public class PageMetaDataMapper implements Mapper<PageMetaData> {

    public PageMetaData toEntity(@NotNull final Node node) {

        try {
            final PageMetaData.Builder builder = PageMetaData.builder();
            final PropertyIterator propertiesIterator = node.getProperties(CmsProperty.META_DATA_PREFIX);
            while (propertiesIterator.hasNext()) {
                final Property property = propertiesIterator.nextProperty();
                builder.put(property.getName().substring(CmsProperty.META_DATA_PREFIX.length()), property.getString());
            }
            return builder.build();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public void toNode(@NotNull final PageMetaData metaData, @NotNull final Node node) {

        metaData.forEach(entry -> {
            try {
                node.setProperty(CmsProperty.META_DATA_PREFIX + entry.getKey(), entry.getValue());
            } catch (final RepositoryException e) {
                throw new RuntimeRepositoryException(e);
            }
        });
    }
}
