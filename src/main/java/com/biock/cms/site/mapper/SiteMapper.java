package com.biock.cms.site.mapper;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.PropertyUtils;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.mapper.ContactDataMapper;
import com.biock.cms.shared.mapper.Mapper;
import com.biock.cms.shared.mapper.ModificationMapper;
import com.biock.cms.site.Site;
import com.biock.cms.site.builder.SiteBuilder;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component
public class SiteMapper implements Mapper<Site> {

    private final ModificationMapper modificationMapper;
    private final LanguagesMapper languagesMapper;
    private final ContactDataMapper contactDataMapper;

    public SiteMapper(
            final ModificationMapper modificationMapper,
            final LanguagesMapper languagesMapper,
            final ContactDataMapper contactDataMapper) {

        this.modificationMapper = modificationMapper;
        this.languagesMapper = languagesMapper;
        this.contactDataMapper = contactDataMapper;
    }

    @Override
    public SiteBuilder toEntityBuilder(final Node node) {

        try {
            return Site.builder()
                    .id(node.getName())
                    .description(getStringProperty(node, Property.JCR_DESCRIPTION))
                    .modification(this.modificationMapper.toEntity(node))
                    .active(PropertyUtils.getBooleanProperty(node, CmsProperty.ACTIVE))
                    .layout(getStringProperty(node, CmsProperty.LAYOUT))
                    .theme(getStringProperty(node, CmsProperty.THEME))
                    .homePage(getStringProperty(node, CmsProperty.HOME_PAGE))
                    .languages(this.languagesMapper.map(node, CmsNode.LANGUAGES))
                    .contactData(this.contactDataMapper.map(node, CmsNode.CONTACT_DATA));
        } catch (RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final Site entity, final Node node) {

    }
}
