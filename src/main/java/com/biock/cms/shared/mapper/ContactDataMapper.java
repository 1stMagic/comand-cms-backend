package com.biock.cms.shared.mapper;

import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.ContactData;
import com.biock.cms.shared.builder.ContactDataBuilder;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component
public class ContactDataMapper implements Mapper<ContactData> {

    @Override
    public ContactDataBuilder toEntityBuilder(final Node node) {

        return ContactData.builder()
                .company(getStringProperty(node, CmsProperty.CONTACT_DATA_COMPANY))
                .street(getStringProperty(node, CmsProperty.CONTACT_DATA_STREET))
                .postalCode(getStringProperty(node, CmsProperty.CONTACT_DATA_POSTAL_CODE))
                .city(getStringProperty(node, CmsProperty.CONTACT_DATA_CITY))
                .phone(getStringProperty(node, CmsProperty.CONTACT_DATA_PHONE))
                .mobile(getStringProperty(node, CmsProperty.CONTACT_DATA_MOBILE))
                .fax(getStringProperty(node, CmsProperty.CONTACT_DATA_FAX))
                .email(getStringProperty(node, CmsProperty.CONTACT_DATA_EMAIL))
                .country(getStringProperty(node, CmsProperty.CONTACT_DATA_COUNTRY));
    }

    @Override
    public void toNode(final ContactData entity, final Node node) {

    }

    public ContactData map(final Node node, final String nodeName) {

        try {
            if (node.hasNode(nodeName)) {
                final Node contactDataNode = node.getNode(nodeName);
                if (CmsType.CONTACT_DATA.isNodeType(contactDataNode)) {
                    return toEntity(contactDataNode);
                }
            }
            return ContactData.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
