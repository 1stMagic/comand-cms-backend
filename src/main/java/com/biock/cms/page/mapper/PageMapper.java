package com.biock.cms.page.mapper;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.component.mapper.ComponentsMapper;
import com.biock.cms.i18n.mapper.TranslationMapper;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.page.Page;
import com.biock.cms.page.builder.PageBuilder;
import com.biock.cms.shared.EntityId;
import com.biock.cms.shared.mapper.Mapper;
import com.biock.cms.shared.mapper.ModificationMapper;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

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
                    .id(new EntityId(node.getName()))
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
                    .external(getBooleanProperty(node, CmsProperty.EXTERNAL, false))
                    .href(getStringProperty(node, CmsProperty.HREF, ""))
                    .target(getStringProperty(node, CmsProperty.TARGET, ""))
                    .metaData(this.metaDataMapper.map(node, CmsNode.META_DATA))
                    .jcrPath(node.getPath())
                    .components(this.componentsMapper.toEntity(node));
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final Page entity, final Node node) {

    }

}
