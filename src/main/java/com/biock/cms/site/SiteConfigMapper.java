package com.biock.cms.site;

import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;

import static com.biock.cms.jcr.PropertyUtils.getStringArrayProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

public class SiteConfigMapper {

    public SiteConfig toSiteConfig(@NotNull final Node node) {

        return SiteConfig.builder()
                .layout(getStringProperty(node, CmsProperty.LAYOUT))
                .theme(getStringProperty(node, CmsProperty.THEME))
                .language(getStringProperty(node, CmsProperty.LANGUAGE))
                .homePage(getStringProperty(node, CmsProperty.HOME_PAGE))
                .supportedLanguage(getStringArrayProperty(node, CmsProperty.SUPPORTED_LANGUAGES))
                .build();
    }

    public void toNode(@NotNull final SiteConfig config, @NotNull final Node node) {

        try {
            node.setProperty(CmsProperty.LAYOUT, config.getLayout());
            node.setProperty(CmsProperty.THEME, config.getTheme());
            node.setProperty(CmsProperty.LANGUAGE, config.getLanguage());
            node.setProperty(CmsProperty.HOME_PAGE, config.getHomePage());
            node.setProperty(CmsProperty.SUPPORTED_LANGUAGES, config.getSupportedLanguages());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
