package com.biock.cms.shared.mapper;

import com.biock.cms.CmsNode;
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
                .company(getStringProperty(node, CmsProperty.CONTACT_DATA_COMPANY, ""))
                .street(getStringProperty(node, CmsProperty.CONTACT_DATA_STREET, ""))
                .postalCode(getStringProperty(node, CmsProperty.CONTACT_DATA_POSTAL_CODE, ""))
                .city(getStringProperty(node, CmsProperty.CONTACT_DATA_CITY, ""))
                .phone(getStringProperty(node, CmsProperty.CONTACT_DATA_PHONE, ""))
                .mobile(getStringProperty(node, CmsProperty.CONTACT_DATA_MOBILE, ""))
                .fax(getStringProperty(node, CmsProperty.CONTACT_DATA_FAX, ""))
                .email(getStringProperty(node, CmsProperty.CONTACT_DATA_EMAIL, ""))
                .country(getStringProperty(node, CmsProperty.CONTACT_DATA_COUNTRY, ""));
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

    public void map(final ContactData contactData, final Node node, final String nodeName) {

        try {
            if (contactData == null) {
                return;
            }
            final Node contactDataNode = node.hasNode(nodeName)
                    ? node.getNode(nodeName)
                    : node.addNode(nodeName, CmsType.CONTACT_DATA.getName());
            contactDataNode.setProperty(CmsProperty.CONTACT_DATA_COMPANY, contactData.getCompany());
            contactDataNode.setProperty(CmsProperty.CONTACT_DATA_STREET, contactData.getStreet());
            contactDataNode.setProperty(CmsProperty.CONTACT_DATA_POSTAL_CODE, contactData.getPostalCode());
            contactDataNode.setProperty(CmsProperty.CONTACT_DATA_CITY, contactData.getCity());
            contactDataNode.setProperty(CmsProperty.CONTACT_DATA_PHONE, contactData.getPhone());
            contactDataNode.setProperty(CmsProperty.CONTACT_DATA_MOBILE, contactData.getMobile());
            contactDataNode.setProperty(CmsProperty.CONTACT_DATA_FAX, contactData.getFax());
            contactDataNode.setProperty(CmsProperty.CONTACT_DATA_EMAIL, contactData.getEmail());
            contactDataNode.setProperty(CmsProperty.CONTACT_DATA_COUNTRY, contactData.getCountry());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
