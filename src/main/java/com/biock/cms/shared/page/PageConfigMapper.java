package com.biock.cms.shared.page;

import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.LabelMapper;
import com.biock.cms.shared.Mapper;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

public class PageConfigMapper implements Mapper<PageConfig> {

    public PageConfig.Builder toEntityBuilder(@NotNull final Node node) {

        return PageConfig.builder()
                .showInMainNavigation(getBooleanProperty(node, CmsProperty.SHOW_IN_MAIN_NAVIGATION))
                .showInTopNavigation(getBooleanProperty(node, CmsProperty.SHOW_IN_TOP_NAVIGATION, false))
                .showInFooterNavigation(getBooleanProperty(node, CmsProperty.SHOW_IN_FOOTER_NAVIGATION, false))
                .external(getBooleanProperty(node, CmsProperty.EXTERNAL, false))
                .href(getStringProperty(node, CmsProperty.HREF, ""))
                .target(getStringProperty(node, CmsProperty.TARGET, ""))
                .iconClass(getStringProperty(node, CmsProperty.ICON_CLASS, ""))
                .mainNavigationTitle(new LabelMapper("mainNavigationTitle").toEntity(node))
                .topNavigationTitle(new LabelMapper("topNavigationTitle").toEntity(node))
                .footerNavigationTitle(new LabelMapper("footerNavigationTitle").toEntity(node));
    }

    public void toNode(@NotNull final PageConfig config, @NotNull final Node node) {

        try {
            node.setProperty(CmsProperty.SHOW_IN_MAIN_NAVIGATION, config.isShowInMainNavigation());
            node.setProperty(CmsProperty.SHOW_IN_TOP_NAVIGATION, config.isShowInTopNavigation());
            node.setProperty(CmsProperty.SHOW_IN_FOOTER_NAVIGATION, config.isShowInFooterNavigation());
            node.setProperty(CmsProperty.EXTERNAL, config.isExternal());
            node.setProperty(CmsProperty.HREF, config.getHref());
            node.setProperty(CmsProperty.TARGET, config.getTarget());
            node.setProperty(CmsProperty.ICON_CLASS, config.getIconClass());
            if (config.hasMainNavigationTitle()) {
                new LabelMapper("mainNavigationTitle").toNode(config.getMainNavigationTitle(), node);
            }
            if (config.hasTopNavigationTitle()) {
                new LabelMapper("topNavigationTitle").toNode(config.getTopNavigationTitle(), node);
            }
            if (config.hasFooterNavigationTitle()) {
                new LabelMapper("footerNavigationTitle").toNode(config.getFooterNavigationTitle(), node);
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
