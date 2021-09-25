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

        try {
            return User.builder()
                    .id(node.getName())
                    .email(getStringProperty(node, CmsProperty.USER_EMAIL))
                    .salutation(getStringProperty(node, CmsProperty.USER_SALUTATION, ""))
                    .title(getStringProperty(node, CmsProperty.USER_TITLE, ""))
                    .firstName(getStringProperty(node, CmsProperty.USER_FIRST_NAME, ""))
                    .lastName(getStringProperty(node, CmsProperty.USER_LAST_NAME, ""))
                    .active(getBooleanProperty(node, CmsProperty.ACTIVE, false))
                    .groups(getStringArrayProperty(node, CmsProperty.USER_GROUPS))
                    .contactData(this.contactDataMapper.map(node, CmsNode.CONTACT_DATA));
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final User entity, final Node node) {

    }
}
