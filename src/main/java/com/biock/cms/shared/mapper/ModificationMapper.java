package com.biock.cms.shared.mapper;

import com.biock.cms.config.CmsConfig;
import com.biock.cms.shared.Modification;
import com.biock.cms.shared.builder.ModificationBuilder;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;

import java.time.ZoneOffset;

import static com.biock.cms.jcr.PropertyUtils.getDateProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;
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

    }
}
