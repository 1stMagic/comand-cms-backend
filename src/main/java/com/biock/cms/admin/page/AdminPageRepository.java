package com.biock.cms.admin.page;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsType;
import com.biock.cms.jcr.CloseableJcrSession;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.NodeUtils;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.utils.StreamUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import static com.biock.cms.jcr.NodeUtils.getSiteNode;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@org.springframework.stereotype.Repository
public class AdminPageRepository {

    private static final Logger LOG = LoggerFactory.getLogger(AdminPageRepository.class);

    private final Repository repository;
    private final AdminPageMapper pageMapper;

    public AdminPageRepository(final Repository repository, final AdminPageMapper pageMapper) {

        this.repository = repository;
        this.pageMapper = pageMapper;
    }

    public List<AdminPage> getPages(@NotNull final String siteName, @NotNull final Predicate<Node> filter) {

        return getChildPages(JcrPaths.absolute(CmsNode.CMS, CmsNode.SITES, siteName), filter);
    }

    public List<AdminPage> getPagesAsFlatList(@NotNull final String siteName, @NotNull final Predicate<Node> filter) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final List<Node> nodes = new ArrayList<>();
            fetchPageNodes(getSiteNode(session, siteName), filter, nodes);
            return nodes.stream().map(this.pageMapper::toEntity).collect(toList());
        }
    }

    public List<AdminPage> getChildPages(@NotNull final String absolutePath, @NotNull final Predicate<Node> filter) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final var node = session.getNode(absolutePath);
            if (node != null) {
                return StreamUtils.stream(node.getNodes())
                        .filter(CmsType.PAGE.typeFilter())
                        .filter(filter)
                        .map(this.pageMapper::toEntityBuilder)
                        .map(builder -> addChildren(builder, filter))
                        .map(AdminPage.Builder::build)
                        .collect(toList());
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }

        return emptyList();
    }

    public Optional<AdminPage> save(@NotNull final String siteName, @NotNull final AdminPage adminPage) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final var parentNode = StringUtils.isBlank(adminPage.getParentId())
                    ? getSiteNode(session, siteName)
                    : NodeUtils.getNodeById(session, adminPage.getParentId());
            if (parentNode == null) {
                LOG.error("Parent node node not found: site = {}, parentId = {}", siteName, adminPage.getParentId());
                return Optional.empty();
            }
            final var id = StringUtils.defaultIfBlank(adminPage.getId(), UUID.randomUUID().toString());
            if (parentNode.hasNode(id)) {
                throw new UnsupportedOperationException("Update of nodes not implemented yet");
            } else {
                final var adminPageNode = parentNode.addNode(adminPage.getDescriptor().getName(), CmsType.PAGE.getName());
                this.pageMapper.toNode(AdminPage.builder(adminPage).id(id).build(), adminPageNode);
            }
            session.save();
            final Node savedAdminPageNode = NodeUtils.getNodeById(session, id);
            if (savedAdminPageNode == null) {
                LOG.error("Unable to find node by ID after saving: {}", id);
                return Optional.empty();
            }
            return Optional.of(this.pageMapper.toEntity(savedAdminPageNode));
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private void fetchPageNodes(final Node node, @NotNull final Predicate<Node> filter, @NotNull final List<Node> nodes) {

        if (node != null) {
            if (CmsType.PAGE.isNodeType(node) && filter.test(node)) {
                nodes.add(node);
            }
            StreamUtils.streamChildren(node).forEach(child -> fetchPageNodes(child, filter, nodes));
        }
    }

    private AdminPage.Builder addChildren(@NotNull final AdminPage.Builder builder, @NotNull final Predicate<Node> filter) {

        return builder.children(getChildPages(builder.path(), filter));
    }
}
