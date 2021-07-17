package com.biock.cms.site;

import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.Builder;
import com.biock.cms.shared.Mapper;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;

import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

public class SiteContactDataMapper implements Mapper<SiteContactData> {

    @Override
    public Builder<SiteContactData> toEntityBuilder(@NotNull final Node node) {

        return SiteContactData.builder()
                .salutation(getStringProperty(node, CmsProperty.CONTACT_DATA_SALUTATION, ""))
                .title(getStringProperty(node, CmsProperty.CONTACT_DATA_TITLE, ""))
                .firstName(getStringProperty(node, CmsProperty.CONTACT_DATA_FIRST_NAME, ""))
                .lastName(getStringProperty(node, CmsProperty.CONTACT_DATA_LAST_NAME, ""))
                .street(getStringProperty(node, CmsProperty.CONTACT_DATA_STREET, ""))
                .postalCode(getStringProperty(node, CmsProperty.CONTACT_DATA_POSTAL_CODE, ""))
                .city(getStringProperty(node, CmsProperty.CONTACT_DATA_CITY, ""))
                .phone(getStringProperty(node, CmsProperty.CONTACT_DATA_PHONE, ""))
                .fax(getStringProperty(node, CmsProperty.CONTACT_DATA_FAX, ""))
                .email(getStringProperty(node, CmsProperty.CONTACT_DATA_EMAIL, ""));
    }

    @Override
    public void toNode(@NotNull final SiteContactData entity, @NotNull final Node node) {

        try {
            node.setProperty(CmsProperty.CONTACT_DATA_SALUTATION, entity.getSalutation());
            node.setProperty(CmsProperty.CONTACT_DATA_TITLE, entity.getTitle());
            node.setProperty(CmsProperty.CONTACT_DATA_FIRST_NAME, entity.getFirstName());
            node.setProperty(CmsProperty.CONTACT_DATA_LAST_NAME, entity.getLastName());
            node.setProperty(CmsProperty.CONTACT_DATA_STREET, entity.getStreet());
            node.setProperty(CmsProperty.CONTACT_DATA_POSTAL_CODE, entity.getPostalCode());
            node.setProperty(CmsProperty.CONTACT_DATA_CITY, entity.getCity());
            node.setProperty(CmsProperty.CONTACT_DATA_PHONE, entity.getPhone());
            node.setProperty(CmsProperty.CONTACT_DATA_FAX, entity.getFax());
            node.setProperty(CmsProperty.CONTACT_DATA_EMAIL, entity.getEmail());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
