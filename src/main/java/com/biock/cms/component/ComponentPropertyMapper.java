package com.biock.cms.component;

import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.Mapper;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import java.math.BigDecimal;

public class ComponentPropertyMapper implements Mapper<ComponentProperty> {

    @Override
    public ComponentProperty toEntity(final Node node) {

        try {
            return ComponentProperty.builder()
                    .name(node.getName())
                    .value(new ComponentPropertiesMapper().toEntity(node))
                    .build();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public ComponentProperty toEntity(final Property property) {

        try {
            return ComponentProperty.builder()
                    .name(property.getName())
                    .value(getValue(property))
                    .build();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final ComponentProperty entity, final Node node) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Object getValue(final Property property) {

        try {
            if (property.getType() == PropertyType.STRING) {
                return property.getString();
            }
            if (property.getType() == PropertyType.LONG) {
                return property.getLong();
            }
            if (property.getType() == PropertyType.DECIMAL) {
                return property.getDecimal();
            }
            if (property.getType() == PropertyType.DOUBLE) {
                return BigDecimal.valueOf(property.getDouble());
            }
            if (property.getType() == PropertyType.BOOLEAN) {
                return property.getBoolean();
            }
            return null;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
