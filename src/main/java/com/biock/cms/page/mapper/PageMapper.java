package com.biock.cms.page.mapper;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.component.mapper.ComponentsMapper;
import com.biock.cms.i18n.mapper.TranslationMapper;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.page.Page;
import com.biock.cms.page.builder.PageBuilder;
import com.biock.cms.shared.mapper.Mapper;
import com.biock.cms.shared.mapper.ModificationMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import java.util.Optional;

import static com.biock.cms.jcr.PropertyUtils.*;

@Component
public class PageMapper implements Mapper<Page> {

    private final ModificationMapper modificationMapper;
    private final TranslationMapper translationMapper;
    private final MetaDataMapper metaDataMapper;
    private final ComponentsMapper componentsMapper;

    public PageMapper(
            final ModificationMapper modificationMapper,
            final TranslationMapper translationMapper,
            final MetaDataMapper metaDataMapper,
            final ComponentsMapper componentsMapper) {

        this.modificationMapper = modificationMapper;
        this.translationMapper = translationMapper;
        this.metaDataMapper = metaDataMapper;
        this.componentsMapper = componentsMapper;
    }

    @Override
    public PageBuilder toEntityBuilder(final Node node) {

        try {
            return Page.builder()
                    .id(getStringProperty(node, Property.JCR_ID))
                    .name(getStringProperty(node, Property.JCR_NAME, ""))
                    .description(getStringProperty(node, Property.JCR_DESCRIPTION))
                    .modification(this.modificationMapper.toEntity(node))
                    .active(getBooleanProperty(node, CmsProperty.ACTIVE))
                    .topNavigationTitle(this.translationMapper.map(node, CmsNode.TOP_NAVIGATION_TITLE))
                    .mainNavigationTitle(this.translationMapper.map(node, CmsNode.MAIN_NAVIGATION_TITLE))
                    .footerNavigationTitle(this.translationMapper.map(node, CmsNode.FOOTER_NAVIGATION_TITLE))
                    .showInTopNavigation(getBooleanProperty(node, CmsProperty.SHOW_IN_TOP_NAVIGATION, false))
                    .showInMainNavigation(getBooleanProperty(node, CmsProperty.SHOW_IN_MAIN_NAVIGATION, false))
                    .showInFooterNavigation(getBooleanProperty(node, CmsProperty.SHOW_IN_FOOTER_NAVIGATION, false))
                    .iconClass(getStringProperty(node, CmsProperty.ICON_CLASS, ""))
                    .navigationEntry(getBooleanProperty(node, CmsProperty.NAVIGATION_ENTRY, false))
                    .media(getBooleanProperty(node, CmsProperty.MEDIA, false))
                    .external(getBooleanProperty(node, CmsProperty.EXTERNAL, false))
                    .href(getStringProperty(node, CmsProperty.HREF, ""))
                    .target(getStringProperty(node, CmsProperty.TARGET, ""))
                    .requiredGroups(getStringArrayProperty(node, CmsProperty.REQUIRED_GROUPS, new String[0]))
                    .metaData(this.metaDataMapper.map(node, CmsNode.META_DATA))
                    .jcrPath(node.getPath())
                    .components(this.componentsMapper.toEntity(node));
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final Page entity, final Node node) {

        try {
            node.setProperty(Property.JCR_ID, entity.getId());
            node.setProperty(Property.JCR_NAME, StringUtils.defaultString(entity.getName()));
            node.setProperty(Property.JCR_DESCRIPTION, StringUtils.defaultString(entity.getDescription()));
            if (entity.getModification() != null) {
                this.modificationMapper.toNode(entity.getModification(), node);
            }
            node.setProperty(CmsProperty.ACTIVE, entity.isActive());
            this.translationMapper.map(entity.getTopNavigationTitle(), node, CmsNode.TOP_NAVIGATION_TITLE);
            this.translationMapper.map(entity.getMainNavigationTitle(), node, CmsNode.MAIN_NAVIGATION_TITLE);
            this.translationMapper.map(entity.getFooterNavigationTitle(), node, CmsNode.FOOTER_NAVIGATION_TITLE);
            node.setProperty(CmsProperty.SHOW_IN_TOP_NAVIGATION, entity.isShowInTopNavigation());
            node.setProperty(CmsProperty.SHOW_IN_MAIN_NAVIGATION, entity.isShowInMainNavigation());
            node.setProperty(CmsProperty.SHOW_IN_FOOTER_NAVIGATION, entity.isShowInFooterNavigation());
            node.setProperty(CmsProperty.ICON_CLASS, StringUtils.defaultString(entity.getIconClass()));
            node.setProperty(CmsProperty.NAVIGATION_ENTRY, entity.isNavigationEntry());
            node.setProperty(CmsProperty.MEDIA, entity.isExternal());
            node.setProperty(CmsProperty.EXTERNAL, entity.isExternal());
            node.setProperty(CmsProperty.HREF, StringUtils.defaultString(entity.getHref()));
            node.setProperty(CmsProperty.TARGET, StringUtils.defaultString(entity.getTarget()));
            node.setProperty(CmsProperty.REQUIRED_GROUPS, Optional.ofNullable(entity.getRequiredGroups()).orElse(new String[0]));
            if (entity.getMetaData() != null) {
                this.metaDataMapper.map(entity.getMetaData(), node, CmsNode.META_DATA);
            }
//            if (entity.getComponents() != null) {
//                this.componentsMapper.toNode(entity.getComponents(), node);
//            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

}
