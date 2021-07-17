package com.biock.cms.shared;

import com.biock.cms.jcr.exception.RuntimeRepositoryException;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

public class DescriptorMapper implements Mapper<Descriptor> {

    @Override
    public Descriptor.Builder toEntityBuilder(final Node node) {

        return Descriptor.builder()
                .name(getStringProperty(node, Property.JCR_NAME))
                .title(getStringProperty(node, Property.JCR_TITLE))
                .description(getStringProperty(node, Property.JCR_DESCRIPTION));
    }

    @Override
    public void toNode(final Descriptor descriptor, final Node node) {

        try {
            node.setProperty(Property.JCR_NAME, descriptor.getName());
            node.setProperty(Property.JCR_TITLE, descriptor.getTitle());
            node.setProperty(Property.JCR_DESCRIPTION, descriptor.getDescription());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
