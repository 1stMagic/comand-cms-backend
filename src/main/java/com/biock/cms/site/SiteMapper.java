package com.biock.cms.site;

import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.Mapper;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;
import java.time.ZoneOffset;

import static com.biock.cms.jcr.PropertyUtils.*;
import static com.biock.cms.utils.DateUtils.toCalendar;
import static com.biock.cms.utils.DateUtils.toOffsetDateTime;

public class SiteMapper implements Mapper<Site> {

    private final ZoneOffset timeZoneOffset;

    public SiteMapper(@NotNull final ZoneOffset timeZoneOffset) {

        this.timeZoneOffset = timeZoneOffset;
    }

    @Override
    public Site toEntity(@NotNull final Node node) {

        return Site.builder()
                .name(getStringProperty(node, Property.JCR_NAME))
                .title(getStringProperty(node, Property.JCR_TITLE))
                .description(getStringProperty(node, Property.JCR_DESCRIPTION))
                .created(toOffsetDateTime(getDateProperty(node, Property.JCR_CREATED), this.timeZoneOffset))
                .createdBy(getStringProperty(node, Property.JCR_CREATED_BY))
                .lastModified(toOffsetDateTime(getDateProperty(node, Property.JCR_LAST_MODIFIED), this.timeZoneOffset))
                .lastModifiedBy(getStringProperty(node, Property.JCR_LAST_MODIFIED_BY))
                .active(getBooleanProperty(node, CmsProperty.ACTIVE))
                .config(SiteConfig.builder()
                        .layout(getStringProperty(node, CmsProperty.LAYOUT))
                        .theme(getStringProperty(node, CmsProperty.THEME))
                        .language(getStringProperty(node, CmsProperty.LANGUAGE))
                        .homePage(getStringProperty(node, CmsProperty.HOME_PAGE))
                        .supportedLanguage(getStringArrayProperty(node, CmsProperty.SUPPORTED_LANGUAGES))
                        .build())
                .build();
    }

    @Override
    public void toNode(@NotNull final Site site, @NotNull final Node node) {

        try {
            node.setProperty(Property.JCR_NAME, site.getName());
            node.setProperty(Property.JCR_TITLE, site.getTitle());
            node.setProperty(Property.JCR_DESCRIPTION, site.getDescription());
            node.setProperty(Property.JCR_CREATED, toCalendar(site.getCreated()));
            node.setProperty(Property.JCR_CREATED_BY, site.getCreatedBy());
            node.setProperty(Property.JCR_LAST_MODIFIED, toCalendar(site.getLastModified()));
            node.setProperty(Property.JCR_LAST_MODIFIED_BY, site.getLastModifiedBy());
            node.setProperty(CmsProperty.ACTIVE, site.isActive());

            final SiteConfig config = site.getConfig();
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
