package com.biock.cms.site;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.Mapper;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;

import java.util.List;

import static com.biock.cms.jcr.PropertyUtils.getStringProperty;
import static java.util.Collections.emptyList;

public class SiteConfigMapper implements Mapper<SiteConfig> {

    public SiteConfig toEntity(@NotNull final Node node) {

        return SiteConfig.builder()
                .layout(getStringProperty(node, CmsProperty.LAYOUT))
                .theme(getStringProperty(node, CmsProperty.THEME))
                .language(getStringProperty(node, CmsProperty.LANGUAGE))
                .homePage(getStringProperty(node, CmsProperty.HOME_PAGE))
                .supportedLanguages(getSupportedLanguages(node))
                .build();
    }

    public void toNode(@NotNull final SiteConfig config, @NotNull final Node node) {

        try {
            node.setProperty(CmsProperty.LAYOUT, config.getLayout());
            node.setProperty(CmsProperty.THEME, config.getTheme());
            node.setProperty(CmsProperty.LANGUAGE, config.getLanguage());
            node.setProperty(CmsProperty.HOME_PAGE, config.getHomePage());
            setSupportedLanguages(config, node);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private List<SupportedLanguage> getSupportedLanguages(@NotNull final Node node) {

        try {
            if (node.hasNode(CmsNode.SUPPORTED_LANGUAGES)) {
                return new SupportedLanguagesMapper().toEntity(node.getNode(CmsNode.SUPPORTED_LANGUAGES));
            }
            return emptyList();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private void setSupportedLanguages(@NotNull final SiteConfig config, @NotNull final Node node) {

        try {
            final Node supportedLanguagesNode = node.hasNode(CmsNode.SUPPORTED_LANGUAGES)
                    ? node.getNode(CmsNode.SUPPORTED_LANGUAGES)
                    : node.addNode(CmsNode.SUPPORTED_LANGUAGES);
            new SupportedLanguagesMapper().toNode(config.getSupportedLanguages(), supportedLanguagesNode);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
