package com.biock.cms.user.mapper;

import com.biock.cms.CmsType;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.builder.Builder;
import com.biock.cms.shared.builder.BuilderAdapter;
import com.biock.cms.shared.mapper.Mapper;
import com.biock.cms.user.UserGroup;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class UserGroupsMapper implements Mapper<List<UserGroup>> {

    private final UserGroupMapper userGroupMapper;

    public UserGroupsMapper(final UserGroupMapper userGroupMapper) {

        this.userGroupMapper = userGroupMapper;
    }

    @Override
    public Builder<List<UserGroup>> toEntityBuilder(final Node node) {

        try {
            final List<UserGroup> userGroups = new ArrayList<>();
            final NodeIterator userGroupNodes = node.getNodes();
            while (userGroupNodes.hasNext()) {
                final Node userGroupNode = userGroupNodes.nextNode();
                if (CmsType.USER_GROUP.isNodeType(userGroupNode)) {
                    userGroups.add(this.userGroupMapper.toEntity(userGroupNode));
                }
            }
            return new BuilderAdapter<>(userGroups);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final List<UserGroup> entity, final Node node) {

    }

    public List<UserGroup> map(final Node node, final String nodeName) {

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
