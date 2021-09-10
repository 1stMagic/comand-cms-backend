package com.biock.cms.user.mapper;

import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.PropertyUtils;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.mapper.Mapper;
import com.biock.cms.user.User;
import com.biock.cms.user.builder.UserBuilder;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import static com.biock.cms.jcr.PropertyUtils.*;

@Component
public class UserMapper implements Mapper<User> {

    @Override
    public UserBuilder toEntityBuilder(final Node node) {

        try {
            return User.builder()
                    .id(node.getName())
                    .email(getStringProperty(node, CmsProperty.USER_EMAIL))
                    .active(getBooleanProperty(node, CmsProperty.ACTIVE, false))
                    .groups(getStringArrayProperty(node, CmsProperty.USER_GROUPS));
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final User entity, final Node node) {

    }
}
