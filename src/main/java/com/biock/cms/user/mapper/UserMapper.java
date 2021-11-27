package com.biock.cms.user.mapper;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.mapper.ContactDataMapper;
import com.biock.cms.shared.mapper.Mapper;
import com.biock.cms.user.User;
import com.biock.cms.user.builder.UserBuilder;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import static com.biock.cms.jcr.PropertyUtils.*;

@Component
public class UserMapper implements Mapper<User> {

    private final ContactDataMapper contactDataMapper;

    public UserMapper(final ContactDataMapper contactDataMapper) {

        this.contactDataMapper = contactDataMapper;
    }

    @Override
    public UserBuilder toEntityBuilder(final Node node) {

        return User.builder()
                .id(getStringProperty(node, Property.JCR_ID))
                .email(getStringProperty(node, CmsProperty.USER_EMAIL))
                .password(getStringProperty(node, CmsProperty.USER_PASSWORD, ""))
                .salutation(getStringProperty(node, CmsProperty.USER_SALUTATION, ""))
                .title(getStringProperty(node, CmsProperty.USER_TITLE, ""))
                .firstName(getStringProperty(node, CmsProperty.USER_FIRST_NAME, ""))
                .lastName(getStringProperty(node, CmsProperty.USER_LAST_NAME, ""))
                .active(getBooleanProperty(node, CmsProperty.ACTIVE, false))
                .groups(getStringArrayProperty(node, CmsProperty.USER_GROUPS, new String[0]))
                .contactData(this.contactDataMapper.map(node, CmsNode.CONTACT_DATA));
    }

    @Override
    public void toNode(final User entity, final Node node) {

        try {
            if (!node.hasProperty(Property.JCR_ID)) {
                node.setProperty(Property.JCR_ID, entity.getId());
            }
            node.setProperty(CmsProperty.USER_EMAIL, entity.getEmail());
            node.setProperty(CmsProperty.USER_PASSWORD, entity.getPassword());
            node.setProperty(CmsProperty.USER_SALUTATION, entity.getSalutation());
            node.setProperty(CmsProperty.USER_TITLE, entity.getTitle());
            node.setProperty(CmsProperty.USER_FIRST_NAME, entity.getFirstName());
            node.setProperty(CmsProperty.USER_LAST_NAME, entity.getLastName());
            node.setProperty(CmsProperty.ACTIVE, entity.isActive());
            node.setProperty(CmsProperty.USER_GROUPS, entity.getGroups());
            this.contactDataMapper.map(entity.getContactData(), node, CmsNode.CONTACT_DATA);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
