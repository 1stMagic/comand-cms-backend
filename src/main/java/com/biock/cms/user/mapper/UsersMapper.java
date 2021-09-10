package com.biock.cms.user.mapper;

import com.biock.cms.CmsType;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.builder.Builder;
import com.biock.cms.shared.builder.BuilderAdapter;
import com.biock.cms.shared.mapper.Mapper;
import com.biock.cms.user.User;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class UsersMapper implements Mapper<List<User>> {

    private final UserMapper userMapper;

    public UsersMapper(final UserMapper userMapper) {

        this.userMapper = userMapper;
    }

    @Override
    public Builder<List<User>> toEntityBuilder(final Node node) {

        try {
            final List<User> users = new ArrayList<>();
            final NodeIterator userNodes = node.getNodes();
            while (userNodes.hasNext()) {
                final Node userNode = userNodes.nextNode();
                if (CmsType.USER.isNodeType(userNode)) {
                    users.add(this.userMapper.toEntity(userNode));
                }
            }
            return new BuilderAdapter<>(users);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final List<User> entity, final Node node) {

    }

    public List<User> map(final Node node, final String nodeName) {

        try {
            if (node.hasNode(nodeName)) {
                return toEntity(node.getNode(nodeName));
            }
            return emptyList();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
