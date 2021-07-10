package com.biock.cms.shared;

import com.biock.cms.jcr.exception.RuntimeRepositoryException;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import java.time.ZoneOffset;

import static com.biock.cms.jcr.PropertyUtils.getDateProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;
import static com.biock.cms.utils.DateUtils.toCalendar;
import static com.biock.cms.utils.DateUtils.toOffsetDateTime;

public class ModificationMapper implements Mapper<Modification> {

    private final ZoneOffset timeZoneOffset;

    public ModificationMapper(final ZoneOffset timeZoneOffset) {

        this.timeZoneOffset = timeZoneOffset;
    }

    @Override
    public Modification toEntity(final Node node) {

        return Modification.builder()
                .created(toOffsetDateTime(getDateProperty(node, Property.JCR_CREATED), this.timeZoneOffset))
                .createdBy(getStringProperty(node, Property.JCR_CREATED_BY))
                .lastModified(toOffsetDateTime(getDateProperty(node, Property.JCR_LAST_MODIFIED), this.timeZoneOffset))
                .lastModifiedBy(getStringProperty(node, Property.JCR_LAST_MODIFIED_BY))
                .build();
    }

    @Override
    public void toNode(final Modification modification, final Node node) {

        try {
            node.setProperty(Property.JCR_CREATED, toCalendar(modification.getCreated()));
            node.setProperty(Property.JCR_CREATED_BY, modification.getCreatedBy());
            node.setProperty(Property.JCR_LAST_MODIFIED, toCalendar(modification.getLastModified()));
            node.setProperty(Property.JCR_LAST_MODIFIED_BY, modification.getLastModifiedBy());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
