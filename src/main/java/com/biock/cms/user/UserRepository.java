package com.biock.cms.user;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsType;
import com.biock.cms.exception.NodeNotFoundException;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.JcrQueryManager;
import com.biock.cms.jcr.JcrRepository;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.user.builder.UserGroupBuilder;
import com.biock.cms.user.mapper.UserGroupMapper;
import com.biock.cms.user.mapper.UserGroupsMapper;
import com.biock.cms.user.mapper.UsersMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.jcr.*;
import javax.jcr.query.QueryResult;
import java.util.*;

@Service
public class UserRepository extends JcrRepository {

    private final UsersMapper usersMapper;
    private final UserGroupMapper userGroupMapper;
    private final UserGroupsMapper userGroupsMapper;

    public UserRepository(
            final Repository repository,
            final UsersMapper usersMapper,
            final UserGroupMapper userGroupMapper,
            final UserGroupsMapper userGroupsMapper) {

        super(repository);
        this.usersMapper = usersMapper;
        this.userGroupMapper = userGroupMapper;
        this.userGroupsMapper = userGroupsMapper;
    }

    public List<User> getUsers(final String siteId) {

        try (final var session = getSession()) {
            final Optional<Node> siteNode = getSiteNode(session, siteId);
            if (siteNode.isEmpty()) {
                throw new NodeNotFoundException("Site " + siteId);
            }
            return this.usersMapper.map(siteNode.get(), CmsNode.USERS);
        }
    }

    public List<UserGroup> getUserGroups(final String siteId) {

        try (final var session = getSession()) {
            final Optional<Node> siteNode = getSiteNode(session, siteId);
            if (siteNode.isEmpty()) {
                throw new NodeNotFoundException("Site " + siteId);
            }
            return this.userGroupsMapper.map(siteNode.get(), CmsNode.USER_GROUPS);
        }
    }

    public boolean hasUserGroupWithId(final String siteId, final String userGroupId) {

        try (final var session = getSession()) {
            return hasUserGroupWithId(session, siteId, userGroupId);
        }
    }

    public boolean hasUserGroupWithId(final Session session, final String siteId, final String userGroupId) {

        return getUserGroupNodeById(session, siteId, userGroupId).isPresent();
    }

    public Optional<UserGroup> getUserGroupById(final String siteId, final String userGroupId) {

        try (final var session = getSession()) {
            return getUserGroupNodeById(session, siteId, userGroupId).map(this.userGroupMapper::toEntity);
        }
    }

    public UserGroup createUserGroup(final String siteId, final UserGroupBuilder userGroupBuilder) {

        try (final var session = getSession()) {
            final UserGroup userGroup = userGroupBuilder.id(buildUserGroupId(session, siteId, userGroupBuilder)).build();
            final Node userGroupNode = session.getNode(JcrPaths.sites(siteId, CmsNode.USER_GROUPS))
                    .addNode(UUID.randomUUID().toString(), CmsType.USER_GROUP.getName());
            this.userGroupMapper.toNode(userGroup, userGroupNode);
            session.save();
            return userGroup;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private Optional<Node> getUserGroupNodeById(final Session session, final String siteId, final String userGroupId) {

        try {
            final Map<String, String> bindValues = new HashMap<>();
            bindValues.put("$$site", siteId);
            bindValues.put("userGroupId", userGroupId);
            final QueryResult result = JcrQueryManager.executeQuery(
                    session,
                    "select * from [cms:userGroup] as g where isdescendantnode(g, '/cms/sites/$$site/userGroups') and [jcr:id] = $userGroupId",
                    bindValues);
            final NodeIterator nodes = result.getNodes();
            if (nodes.hasNext()) {
                return Optional.of(nodes.nextNode());
            }
            return Optional.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private String buildUserGroupId(final Session session, final String siteId, final UserGroupBuilder userGroupBuilder) {

        int index = 0;
        final String name = StringUtils.defaultIfBlank(
                userGroupBuilder
                    .build()
                    .getName()
                    .getFirstTranslation()
                    .orElse(UUID.randomUUID().toString())
                    .toUpperCase()
                    .replaceAll("[^\\w-]", ""),
                UUID.randomUUID().toString());
        String userGroupId = name;
        while (hasUserGroupWithId(session, siteId, userGroupId)) {
            if (index > 1000) {
                return UUID.randomUUID().toString();
            }
            userGroupId = name + ++index;
        }
        return userGroupId;
    }
}
