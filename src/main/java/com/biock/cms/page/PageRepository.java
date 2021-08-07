package com.biock.cms.page;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;
import com.biock.cms.jcr.CloseableJcrSession;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.NodeUtils;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.Descriptor;
import com.biock.cms.shared.Modification;
import com.biock.cms.site.Site;
import com.biock.cms.utils.StreamUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static com.biock.cms.jcr.NodeUtils.getSiteNode;
import static com.biock.cms.jcr.NodeUtils.getSitesNode;
import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@org.springframework.stereotype.Repository
public class PageRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PageRepository.class);

    private final Repository repository;
    private final PageMapper pageMapper;

    public PageRepository(final Repository repository, final PageMapper pageMapper) {

        this.repository = repository;
        this.pageMapper = pageMapper;
    }

    public Optional<Page> getPage(final String siteName, final String pageId) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final Node pageNode = NodeUtils.getNodeBySiteId(session, siteName, pageId);
            if (pageNode != null && CmsType.PAGE.isNodeType(pageNode)) {
                return Optional.of(this.pageMapper.toEntity(pageNode));
            }
        }
        return Optional.empty();
    }

    public List<Page> getPages(@NotNull final String siteName, @NotNull final Predicate<Node> filter) {

        return getChildPages(JcrPaths.absolute(CmsNode.CMS, CmsNode.SITES, siteName), filter);
    }

    public List<Page> getPagesAsFlatList(@NotNull final String siteName, @NotNull final Predicate<Node> filter) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final List<Node> nodes = new ArrayList<>();
            fetchPageNodes(getSiteNode(session, siteName), filter, nodes);
            return nodes.stream().map(this.pageMapper::toEntity).collect(toList());
        }
    }

    public List<Page> getChildPages(@NotNull final String absolutePath, @NotNull final Predicate<Node> filter) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final var node = session.getNode(absolutePath);
            if (node != null) {
                return StreamUtils.stream(node.getNodes())
                        .filter(CmsType.PAGE.typeFilter())
                        .filter(filter)
                        .map(this.pageMapper::toEntityBuilder)
                        .map(builder -> addChildren(builder, filter))
                        .map(Page.Builder::build)
                        .collect(toList());
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }

        return emptyList();
    }

    public Optional<Page> save(@NotNull final String siteName, @NotNull final Page page) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final var parentNode = StringUtils.isBlank(page.getParentId())
                    ? getSiteNode(session, siteName)
                    : NodeUtils.getNodeById(session, page.getParentId());
            if (parentNode == null) {
                LOG.error("Parent node node not found: site = {}, parentId = {}", siteName, page.getParentId());
                return Optional.empty();
            }
            final var id = StringUtils.defaultIfBlank(page.getId(), UUID.randomUUID().toString());
            final var newPage = !parentNode.hasNode(page.getDescriptor().getName());
            final var modificationBuilder = Modification.builder()
                    .apply(page.getModification())
                    .lastModified(OffsetDateTime.now())
                    .lastModifiedBy("api");
            if (newPage) {
                modificationBuilder.created(OffsetDateTime.now()).createdBy("api");
            }
            final var pageNode = newPage
                    ? parentNode.addNode(page.getDescriptor().getName(), CmsType.PAGE.getName())
                    : parentNode.getNode(page.getDescriptor().getName());
            this.pageMapper.toNode(
                    Page.builder()
                            .apply(page)
                            .id(id)
                            .modification(modificationBuilder.build())
                            .build(),
                    pageNode
            );
            session.save();
            final Node savedPageNode = NodeUtils.getNodeById(session, id);
            if (savedPageNode == null) {
                LOG.error("Unable to find node by ID after saving: {}", id);
                return Optional.empty();
            }
            return Optional.of(this.pageMapper.toEntity(savedPageNode));
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public Optional<Page> clone(final String siteName, final String pageId) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final var pageNode = NodeUtils.getNodeBySiteId(session, siteName, pageId);
            if (pageNode != null) {
                final UnaryOperator<String> copyName = name -> {
                    try {
                        final var format = "copy%s_of_%s";
                        final var parentNode = pageNode.getParent();
                        for (int i = 1; i <= 50; i++) {
                            final var newName = String.format(format, i == 1 ? "" : i, name);
                            if (!parentNode.hasNode(newName)) {
                                return newName;
                            }
                        }
                        return name;
                    } catch (final RepositoryException e) {
                        throw new RuntimeRepositoryException(e);
                    }
                };
                final var sourcePage = this.pageMapper.toEntity(pageNode);
                return save(siteName, Page.builder()
                        .apply(this.pageMapper.toEntity(pageNode))
                        .id(null)
                        .title(sourcePage.getTitle().copy("Copy of %s"))
                        .descriptor(Descriptor.builder()
                                .apply(sourcePage.getDescriptor())
                                .name(copyName.apply(sourcePage.getDescriptor().getName()))
                                .build())
                        .build());
            }
        }
        return Optional.empty();
    }

    public Optional<String> delete(final String siteName, final String pageId) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final var pageNode = NodeUtils.getNodeBySiteId(session, siteName, pageId);
            if (pageNode != null) {
                pageNode.remove();
                session.save();
                return Optional.of(pageId);
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
        return Optional.empty();
    }

    public Optional<Page> getPage(@NotNull final String siteName, @NotNull final String relativePagePath, final boolean onlyActive) {

        LOG.info("{}.getPage(siteName = {}, relativePagePath = {}, onlyActive = {})", getClass().getSimpleName(), siteName, relativePagePath, onlyActive);

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final var siteNode = getSiteNode(session, siteName);
            if (siteNode != null
                    && (!onlyActive || getBooleanProperty(siteNode, CmsProperty.ACTIVE))
                    && siteNode.hasNode(relativePagePath)) {
                final var pageNode = siteNode.getNode(relativePagePath);
                if (!onlyActive || getBooleanProperty(pageNode, CmsProperty.ACTIVE)) {
                    return Optional.of(this.pageMapper.toEntity(pageNode));
                }
            }
            return Optional.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public Optional<Page> getParentPage(@NotNull final Page page, final boolean onlyActive) {

        if (StringUtils.isBlank(page.getParentId())) {
            return Optional.empty();
        }

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final var parentNode = NodeUtils.getNodeById(session, page.getParentId());
            if (parentNode != null && (!onlyActive || getBooleanProperty(parentNode, CmsProperty.ACTIVE))) {
                return Optional.of(this.pageMapper.toEntity(parentNode));
            }
            return Optional.empty();
        }
    }

    public List<Page> getPages(@NotNull final Site site, final boolean onlyActive) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            return getPages(getSitesNode(session).getNode(site.getDescriptor().getName()).getNodes(), onlyActive);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public List<Page> getPages(@NotNull final Page page, final boolean onlyActive) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            return getPages(session.getNode(page.getPath()).getNodes(), onlyActive);
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

    private Page.Builder addChildren(@NotNull final Page.Builder builder, @NotNull final Predicate<Node> filter) {

        return builder.children(getChildPages(builder.path(), filter));
    }

    private List<Page> getPages(@NotNull final NodeIterator pageNodeIterator, final boolean onlyActive) {

        try {
            final List<Page> result = new ArrayList<>();
            while (pageNodeIterator.hasNext()) {
                final var pageNode = pageNodeIterator.nextNode();
                if (CmsType.PAGE.isNodeType(pageNode.getPrimaryNodeType())
                        && (getBooleanProperty(pageNode, CmsProperty.ACTIVE) || !onlyActive)) {
                    result.add(this.pageMapper.toEntity(pageNode));
                }
            }
            return result;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

}
