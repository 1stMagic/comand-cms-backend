package com.biock.cms.admin.page;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;
import com.biock.cms.config.CmsConfig;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.AbstractMapper;
import com.biock.cms.shared.DescriptorMapper;
import com.biock.cms.shared.LabelMapper;
import com.biock.cms.shared.ModificationMapper;
import com.biock.cms.shared.page.PageConfigMapper;
import com.biock.cms.shared.page.PageMetaData;
import com.biock.cms.shared.page.PageMetaDataMapper;
import org.springframework.stereotype.Component;

import javax.jcr.*;
import javax.jcr.nodetype.NodeType;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.biock.cms.jcr.NodeUtils.createIfAbsent;
import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component
public class AdminPageMapper extends AbstractMapper<AdminPage> {

    private final CmsConfig config;

    public AdminPageMapper(final CmsConfig config) {

        this.config = config;
    }

    @Override
    public AdminPage.Builder toEntityBuilder(@NotNull final Node node) {

        return AdminPage.builder()
                .parentId(getParentId(node))
                .id(getStringProperty(node, Property.JCR_ID))
                .descriptor(new DescriptorMapper().toEntity(node))
                .modification(new ModificationMapper(this.config.getTimeZoneOffset()).toEntity(node))
                .active(getBooleanProperty(node, CmsProperty.ACTIVE))
                .title(new LabelMapper(CmsProperty.TITLE).toEntity(node))
                .path(getPath(node))
                .metaData(getPageMetaData(node))
                .config(new PageConfigMapper().toEntity(node));
    }

    @Override
    public void toNode(@NotNull final AdminPage page, @NotNull final Node node) {

        try {
            node.setProperty(Property.JCR_ID, page.getId());
            node.setProperty(CmsProperty.ACTIVE, page.isActive());
            new DescriptorMapper().toNode(page.getDescriptor(), node);
            new ModificationMapper(this.config.getTimeZoneOffset()).toNode(page.getModification(), node);
            new LabelMapper(CmsProperty.TITLE).toNode(page.getTitle(), node);
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
            if (CmsType.PAGE.isNodeType(parent)) {
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
