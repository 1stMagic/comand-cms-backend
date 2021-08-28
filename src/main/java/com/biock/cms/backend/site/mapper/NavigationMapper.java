package com.biock.cms.backend.site.mapper;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.backend.site.Navigation;
import com.biock.cms.backend.site.builder.NavigationBuilder;
import com.biock.cms.i18n.mapper.TranslationMapper;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.mapper.Mapper;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component
public class NavigationMapper implements Mapper<Navigation> {

    private final TranslationMapper translationMapper;

    public NavigationMapper(final TranslationMapper translationMapper) {

        this.translationMapper = translationMapper;
    }

    @Override
    public NavigationBuilder toEntityBuilder(final Node node) {

        try {
            return Navigation.builder()
                    .id(getStringProperty(node, Property.JCR_ID))
                    .topNavigationTitle(this.translationMapper.map(node, CmsNode.TOP_NAVIGATION_TITLE))
                    .mainNavigationTitle(this.translationMapper.map(node, CmsNode.MAIN_NAVIGATION_TITLE))
                    .footerNavigationTitle(this.translationMapper.map(node, CmsNode.FOOTER_NAVIGATION_TITLE))
                    .metaDataTitle(this.translationMapper.map(node, JcrPaths.relative(CmsNode.META_DATA, CmsNode.TITLE)))
                    .description(getStringProperty(node, Property.JCR_DESCRIPTION, ""))
                    .href(getStringProperty(node, CmsProperty.HREF, ""))
                    .navigationEntry(getBooleanProperty(node, CmsProperty.NAVIGATION_ENTRY, false))
                    .media(getBooleanProperty(node, CmsProperty.MEDIA, false))
                    .external(getBooleanProperty(node, CmsProperty.EXTERNAL, false))
                    .active(getBooleanProperty(node, CmsProperty.ACTIVE, false))
                    .jcrPath(node.getPath());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final Navigation entity, final Node node) {

        throw new UnsupportedOperationException("Not implemented");
    }
}
