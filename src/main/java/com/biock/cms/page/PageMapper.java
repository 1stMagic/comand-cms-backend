package com.biock.cms.page;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;
import com.biock.cms.config.CmsConfig;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.AbstractMapper;
import com.biock.cms.shared.Mapper;
import org.springframework.stereotype.Component;

import javax.jcr.*;
import javax.jcr.nodetype.NodeType;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

import static com.biock.cms.jcr.NodeUtils.createIfAbsent;
import static com.biock.cms.jcr.PropertyUtils.*;
import static com.biock.cms.utils.DateUtils.toCalendar;
import static com.biock.cms.utils.DateUtils.toOffsetDateTime;

@Component
public class PageMapper extends AbstractMapper<Page> {

    private final CmsConfig config;
    private final Map<String, Mapper<? extends com.biock.cms.component.Component>> mappers;

    public PageMapper(final CmsConfig config, final Map<String, Mapper<? extends com.biock.cms.component.Component>> mappers) {

        this.config = config;
        this.mappers = mappers;
    }

    @Override
    public Page toEntity(@NotNull final Node node) {

        return Page.builder()
                .parentId(getParentId(node))
                .id(getStringProperty(node, Property.JCR_ID))
                .name(getStringProperty(node, Property.JCR_NAME))
                .title(getStringProperty(node, Property.JCR_TITLE))
                .description(getStringProperty(node, Property.JCR_DESCRIPTION))
                .created(toOffsetDateTime(getDateProperty(node, Property.JCR_CREATED), this.config.getTimeZoneOffset()))
                .createdBy(getStringProperty(node, Property.JCR_CREATED_BY))
                .lastModified(toOffsetDateTime(getDateProperty(node, Property.JCR_LAST_MODIFIED), this.config.getTimeZoneOffset()))
                .lastModifiedBy(getStringProperty(node, Property.JCR_LAST_MODIFIED_BY))
                .active(getBooleanProperty(node, CmsProperty.ACTIVE))
                .headline(getStringProperty(node, CmsProperty.HEADLINE, ""))
                .path(getPath(node))
                .metaData(getPageMetaData(node))
                .config(new PageConfigMapper().toEntity(node))
                .components(getComponents(this.mappers, node))
                .build();
    }

    @Override
    public void toNode(@NotNull final Page page, @NotNull final Node node) {

        try {
            node.setProperty(Property.JCR_ID, page.getId());
            node.setProperty(Property.JCR_NAME, page.getName());
            node.setProperty(Property.JCR_TITLE, page.getTitle());
            node.setProperty(Property.JCR_DESCRIPTION, page.getDescription());
            node.setProperty(Property.JCR_CREATED, toCalendar(page.getCreated()));
            node.setProperty(Property.JCR_CREATED_BY, page.getCreatedBy());
            node.setProperty(Property.JCR_LAST_MODIFIED, toCalendar(page.getLastModified()));
            node.setProperty(Property.JCR_LAST_MODIFIED_BY, page.getLastModifiedBy());
            node.setProperty(CmsProperty.ACTIVE, page.isActive());
            node.setProperty(CmsProperty.HEADLINE, page.getHeadline());
            new PageMetaDataMapper().toNode(
                    page.getMetaData(),
                    createIfAbsent(node, JcrPaths.relative(CmsNode.META_DATA), NodeType.NT_UNSTRUCTURED));
            new PageConfigMapper().toNode(page.getConfig(), node);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private String getParentId(@NotNull final Node pageNode) {

        try {
            final var parent = pageNode.getParent();
            if (parent.getPrimaryNodeType().isNodeType(CmsType.PAGE)) {
                return getStringProperty(parent, Property.JCR_ID);
            }
            return null;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private String getPath(@NotNull final Node pageNode) {

        try {
            return pageNode.getPath();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private PageMetaData getPageMetaData(final Node pageNode) {

        try {
            final Map<String, String> metaData = new HashMap<>();
            if (pageNode.hasNode(JcrPaths.relative(CmsNode.META_DATA))) {
                final PropertyIterator properties = pageNode.getNode(JcrPaths.relative(CmsNode.META_DATA)).getProperties();
                while (properties.hasNext()) {
                    final var property = properties.nextProperty();
                    if (!property.isMultiple() && property.getType() == PropertyType.STRING) {
                        metaData.put(property.getName(), property.getString());
                    }
                }
            }
            return new PageMetaData(metaData);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
