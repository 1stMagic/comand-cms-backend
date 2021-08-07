package com.biock.cms.page;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;
import com.biock.cms.component.ComponentMapper;
import com.biock.cms.config.CmsConfig;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.*;
import com.biock.cms.shared.page.PageConfigMapper;
import com.biock.cms.shared.page.PageMetaData;
import com.biock.cms.shared.page.PageMetaDataMapper;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeType;
import javax.validation.constraints.NotNull;
import java.util.Map;

import static com.biock.cms.jcr.NodeUtils.createIfAbsent;
import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component
public class PageMapper extends AbstractMapper<Page> {

    private final CmsConfig config;
    private final ComponentMapper defaultMapper;
    private final Map<String, Mapper<? extends com.biock.cms.component.Component>> mappers;

    public PageMapper(
            final CmsConfig config,
            final ComponentMapper defaultMapper,
            final Map<String, Mapper<? extends com.biock.cms.component.Component>> mappers) {

        this.config = config;
        this.defaultMapper = defaultMapper;
        this.mappers = mappers;
    }

    @Override
    public Page.Builder toEntityBuilder(@NotNull final Node node) {

        return Page.builder()
                .parentId(getParentId(node))
                .id(getStringProperty(node, Property.JCR_ID))
                .descriptor(new DescriptorMapper().toEntity(node))
                .modification(new ModificationMapper(this.config.getTimeZoneOffset()).toEntity(node))
                .active(getBooleanProperty(node, CmsProperty.ACTIVE))
                .title(new LabelMapper(CmsProperty.TITLE).toEntity(node))
                .path(getPath(node))
                .metaData(getPageMetaData(node))
                .config(new PageConfigMapper().toEntity(node))
                .components(getComponents(this.defaultMapper, this.mappers, node));
    }

    @Override
    public void toNode(@NotNull final Page page, @NotNull final Node node) {

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

    private PageMetaData getPageMetaData(final Node node) {

        try {
            if (node.hasNode(CmsNode.META_DATA)) {
                new PageMetaDataMapper().toEntity(node.getNode(CmsNode.META_DATA));
            }
            return PageMetaData.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
