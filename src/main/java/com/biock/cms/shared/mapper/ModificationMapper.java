package com.biock.cms.shared.mapper;

import com.biock.cms.config.CmsConfig;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.Modification;
import com.biock.cms.shared.builder.ModificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import java.time.ZoneOffset;

import static com.biock.cms.jcr.PropertyUtils.getDateProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;
import static com.biock.cms.utils.DateUtils.toCalendar;
import static com.biock.cms.utils.DateUtils.toOffsetDateTime;

@Component
public class ModificationMapper implements Mapper<Modification> {

    private final CmsConfig config;

    public ModificationMapper(final CmsConfig config) {

        this.config = config;
    }

    @Override
    public ModificationBuilder toEntityBuilder(final Node node) {

        final ZoneOffset timeZoneOffset = this.config.getTimeZoneOffset();
        return Modification.builder()
                .created(toOffsetDateTime(getDateProperty(node, Property.JCR_CREATED), timeZoneOffset))
                .createdBy(getStringProperty(node, Property.JCR_CREATED_BY))
                .lastModified(toOffsetDateTime(getDateProperty(node, Property.JCR_LAST_MODIFIED), timeZoneOffset))
                .lastModifiedBy(getStringProperty(node, Property.JCR_LAST_MODIFIED_BY));
    }

    @Override
    public void toNode(final Modification entity, final Node node) {

        try {
            node.setProperty(Property.JCR_CREATED, toCalendar(entity.getCreated()));
            node.setProperty(Property.JCR_CREATED_BY, StringUtils.defaultString(entity.getCreatedBy()));
            node.setProperty(Property.JCR_LAST_MODIFIED, toCalendar(entity.getLastModified()));
            node.setProperty(Property.JCR_LAST_MODIFIED_BY, StringUtils.defaultString(entity.getLastModifiedBy()));
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
