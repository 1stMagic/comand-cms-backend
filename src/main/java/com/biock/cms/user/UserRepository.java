package com.biock.cms.user;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;
import com.biock.cms.exception.NodeNotFoundException;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.JcrQueryManager;
import com.biock.cms.jcr.JcrRepository;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.user.builder.UserGroupBuilder;
import com.biock.cms.user.mapper.UserGroupMapper;
import com.biock.cms.user.mapper.UserGroupsMapper;
import com.biock.cms.user.mapper.UserMapper;
import com.biock.cms.user.mapper.UsersMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.jcr.*;
import javax.jcr.query.QueryResult;
import java.util.*;

@Service
public class UserRepository extends JcrRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);

    private final UserMapper userMapper;
    private final UsersMapper usersMapper;
    private final UserGroupMapper userGroupMapper;
    private final UserGroupsMapper userGroupsMapper;
    private final PasswordEncoder passwordEncoder;

    public UserRepository(
            final Repository repository,
            final UserMapper userMapper,
            final UsersMapper usersMapper,
            final UserGroupMapper userGroupMapper,
            final UserGroupsMapper userGroupsMapper,
            final PasswordEncoder passwordEncoder) {

        super(repository);
        this.userMapper = userMapper;
        this.usersMapper = usersMapper;
        this.userGroupMapper = userGroupMapper;
        this.userGroupsMapper = userGroupsMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getUser(final String siteId, final String userId) {

        try (final var session = getSession()) {
            final Map<String, String> bindValues = new HashMap<>();
            bindValues.put("$$site", siteId);
            bindValues.put("userId", userId);
            final QueryResult result = JcrQueryManager.executeQuery(
                    session,
                    "select * from [cms:user] as u where isdescendantnode(u, '/cms/sites/$$site/users') and [jcr:id] = $userId",
                    bindValues);
            final NodeIterator nodes = result.getNodes();
            if (nodes.hasNext()) {
                return Optional.of(this.userMapper.toEntity(nodes.nextNode()));
            }
            return Optional.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public Optional<User> getUserByEmail(final String siteId, final String userEmail) {

        try (final var session = getSession()) {
            final Map<String, String> bindValues = new HashMap<>();
            bindValues.put("$$site", siteId);
            bindValues.put("userEmail", userEmail);
            final QueryResult result = JcrQueryManager.executeQuery(
                    session,
                    "select * from [cms:user] as u where isdescendantnode(u, '/cms/sites/$$site/users') and [email] = $userEmail",
                    bindValues);
            final NodeIterator nodes = result.getNodes();
            if (nodes.hasNext()) {
                return Optional.of(this.userMapper.toEntity(nodes.nextNode()));
            }
            return Optional.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
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

    public boolean hasUserWithEmail(final Session session, final String siteId, final String userEmail) {

        return getUserNodeByEmail(session, siteId, userEmail).isPresent();
    }

    public Optional<UserGroup> getUserGroup(final String siteId, final String userGroupId) {

        try (final var session = getSession()) {
            final Map<String, String> bindValues = new HashMap<>();
            bindValues.put("$$site", siteId);
            bindValues.put("userGroupId", userGroupId);
            final QueryResult result = JcrQueryManager.executeQuery(
                    session,
                    "select * from [cms:userGroup] as g where isdescendantnode(g, '/cms/sites/$$site/userGroups') and [jcr:id] = $userGroupId",
                    bindValues);
            final NodeIterator nodes = result.getNodes();
            if (nodes.hasNext()) {
                return Optional.of(this.userGroupMapper.toEntity(nodes.nextNode()));
            }
            return Optional.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
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

    public boolean hasUserGroupWithId(final Session session, final String siteId, final String userGroupId) {

        return getUserGroupNodeById(session, siteId, userGroupId).isPresent();
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

    public String updateUserGroup(final String siteId, final UserGroup userGroup) {

        try (final var session = getSession()) {
            final Optional<Node> userGroupNode = getUserGroupNodeById(session, siteId, userGroup.getId());
            if (userGroupNode.isEmpty()) {
                throw new NodeNotFoundException("UserGroup " + userGroup.getId());
            }
            this.userGroupMapper.toNode(userGroup, userGroupNode.get());
            session.save();
            return userGroup.getId();
        }
    }

    public String deleteUserGroup(final String siteId, final String userGroupId) {

        try (final var session = getSession()) {
            final Optional<Node> userGroupNode = getUserGroupNodeById(session, siteId, userGroupId);
            if (userGroupNode.isEmpty()) {
                throw new NodeNotFoundException("UserGroup " + userGroupId);
            }
            userGroupNode.get().remove();
            session.save();
            return userGroupId;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public Optional<User> createUser(final String siteId, final User user, final String password) {

        try (final var session = getSession()) {
            if (hasUserWithEmail(session, siteId, user.getEmail())) {
                LOG.error("Unable to create user because a user with e-mail '{}' already exists", user.getEmail());
                return Optional.empty();
            }
            final User newUser = User.builder().apply(user).id(UUID.randomUUID().toString()).build();
            final Node userNode = session.getNode(JcrPaths.sites(siteId, CmsNode.USERS))
                    .addNode(newUser.getId(), CmsType.USER.getName());
            this.userMapper.toNode(newUser, userNode);
            userNode.setProperty(
                    CmsProperty.USER_PASSWORD,
                    this.passwordEncoder.encode(StringUtils.defaultIfBlank(password, UUID.randomUUID().toString())));
            session.save();
            return Optional.of(newUser);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public String updateUser(final String siteId, final User user, final String password) {

        try (final var session = getSession()) {
            final Optional<Node> userNode = getUserNodeById(session, siteId, user.getId());
            if (userNode.isEmpty()) {
                throw new NodeNotFoundException("User " + user.getId());
            }
            this.userMapper.toNode(user, userNode.get());
            if (StringUtils.isNotBlank(password)) {
                userNode.get().setProperty(
                        CmsProperty.USER_PASSWORD,
                        this.passwordEncoder.encode(password));
            }
            session.save();
            return user.getId();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public String deleteUser(final String siteId, final String userId) {

        try (final var session = getSession()) {
            final Optional<Node> userNode = getUserNodeById(session, siteId, userId);
            if (userNode.isEmpty()) {
                throw new NodeNotFoundException("User " + userId);
            }
            userNode.get().remove();
            session.save();
            return userId;
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

    private Optional<Node> getUserNodeById(final Session session, final String siteId, final String userId) {

        try {
            final Map<String, String> bindValues = new HashMap<>();
            bindValues.put("$$site", siteId);
            bindValues.put("userId", userId);
            final QueryResult result = JcrQueryManager.executeQuery(
                    session,
                    "select * from [cms:user] as u where isdescendantnode(u, '/cms/sites/$$site/users') and [jcr:id] = $userId",
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

    private Optional<Node> getUserNodeByEmail(final Session session, final String siteId, final String userEmail) {

        try {
            final Map<String, String> bindValues = new HashMap<>();
            bindValues.put("$$site", siteId);
            bindValues.put("userEmail", userEmail);
            final QueryResult result = JcrQueryManager.executeQuery(
                    session,
                    "select * from [cms:user] as u where isdescendantnode(u, '/cms/sites/$$site/users') and [email] = $userEmail",
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
