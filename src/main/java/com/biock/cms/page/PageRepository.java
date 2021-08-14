package com.biock.cms.page;

import com.biock.cms.CmsType;
import com.biock.cms.exception.NodeNotFoundException;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.JcrQuery;
import com.biock.cms.jcr.JcrRepository;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.page.builder.PageBuilder;
import com.biock.cms.page.mapper.PageMapper;
import com.biock.cms.site.Site;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.jcr.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import static com.biock.cms.jcr.NodeFilters.join;
import static com.biock.cms.jcr.NodeFilters.ofType;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;
import static com.biock.cms.utils.Utils.coalesce;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@Service
public class PageRepository extends JcrRepository {

    private final PageMapper pageMapper;

    public PageRepository(final Repository repository, final PageMapper pageMapper) {

        super(repository);
        this.pageMapper = pageMapper;
    }

    public Optional<Page> findPageBySiteIdAndRelativePagePath(final String siteId, final String relativePagePath) {

        try (final var session = getSession()) {
            final String absolutePagePath = JcrPaths.sites(siteId, relativePagePath);
            if (session.hasNode(absolutePagePath)) {
                return Optional.of(this.pageMapper.toEntity(session.getNode(absolutePagePath)));
            }
            return Optional.empty();
        }
    }

    @SafeVarargs
    public final List<Page> getPagesOfSite(final Site site, final Predicate<Node>... nodeFilters) {

        return getPagesOfSite(site.getId(), nodeFilters);
    }

    @SafeVarargs
    public final List<Page> getPagesOfSite(final String siteId, final Predicate<Node>... nodeFilters) {

        try (final var session = getSession()) {
            final Optional<Node> siteNode = getSiteNode(session, siteId);
            if (siteNode.isPresent()) {
                return getChildNodes(siteNode.get(), join(ofType(CmsType.PAGE), nodeFilters))
                        .stream()
                        .map(this.pageMapper::toEntity)
                        .collect(toList());
            }
        }
        return emptyList();
    }

    @SafeVarargs
    public final List<Page> getChildrenOfPage(final Page page, final Predicate<Node>... nodeFilters) {

        try (final var session = getSession()) {
            if (session.hasNode(page.getJcrPath())) {
                final Node pageNode = session.getNode(page.getJcrPath());
                return getChildNodes(pageNode, join(ofType(CmsType.PAGE), nodeFilters))
                        .stream()
                        .map(this.pageMapper::toEntity)
                        .collect(toList());
            }
            return emptyList();
        }
    }

    public String createPage(final String siteId, final PageBuilder pageBuilder, final String parentId, final String afterPageId) {

        try (final var session = getSession()) {
            final Optional<Node> site = getSiteNode(session, siteId);
            if (site.isEmpty()) {
                throw new NodeNotFoundException("Site " + siteId);
            }
            final Optional<Node> parent = StringUtils.isBlank(parentId)
                    ? Optional.empty()
                    : findPageOfSiteById(session, siteId, parentId);
            if (parent.isEmpty() && StringUtils.isNotBlank(parentId)) {
                throw new NodeNotFoundException("Parent page " + parentId);
            }
            return createPage(pageBuilder, requireNonNull(coalesce(parent, site).orElse(null)), afterPageId);
        }
    }

    public String updatePage(final String siteId, final PageBuilder pageBuilder) {

        try (final var session = getSession()) {
            final Page updatedPage = pageBuilder.build();
            final Optional<Node> updatedPageNode = findPageOfSiteById(session, siteId, updatedPage.getId());
            if (updatedPageNode.isEmpty()) {
                throw new NodeNotFoundException("Page " + updatedPage.getId());
            }
            this.pageMapper.toNode(updatedPage, updatedPageNode.get());
            session.save();
            return updatedPage.getId();
        }
    }

    public Page clonePage(final String siteId, final String pageId, final String afterPageId) {

        try (final var session = getSession()) {
            final Optional<Node> pageNodeToClone = findPageOfSiteById(session, siteId, pageId);
            if (pageNodeToClone.isEmpty()) {
                throw new NodeNotFoundException("Page " + pageId);
            }
            final String clonedNodeName = buildClonedNodeName(pageNodeToClone.get());
            final String absSrcPath = pageNodeToClone.get().getPath();
            final String absDestPath = JcrPaths.absolute(pageNodeToClone.get().getParent().getPath(), clonedNodeName);
            session.getWorkspace().copy(absSrcPath, absDestPath);
            final Node clonedNode = session.getNode(absDestPath);
            clonedNode.setProperty(Property.JCR_ID, UUID.randomUUID().toString());
            moveNode(clonedNode, afterPageId);
            return this.pageMapper.toEntity(clonedNode);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public String deletePage(final String siteId, final String pageId) {

        try (final var session = getSession()) {
            final Optional<Node> pageNodeToDelete = findPageOfSiteById(session, siteId, pageId);
            if (pageNodeToDelete.isEmpty()) {
                throw new NodeNotFoundException("Page " + pageId);
            }
            final String deletedPageId = getStringProperty(pageNodeToDelete.get(), Property.JCR_ID);
            pageNodeToDelete.get().remove();
            session.save();
            return deletedPageId;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public Optional<Page> findPageOfSiteById(final String siteId, final String pageId) {

        try (final var session = getSession()) {
            return findPageOfSiteById(session, siteId, pageId).map(this.pageMapper::toEntity);
        }
    }

    private String createPage(final PageBuilder pageBuilder, final Node parent, final String afterPageId) {

        final Page newPage = pageBuilder.id(UUID.randomUUID().toString()).build();
        final Node newPageNode = createNewPageNode(parent, newPage.getName());
        this.pageMapper.toNode(newPage, newPageNode);
        moveNode(newPageNode, afterPageId);
        return newPage.getId();
    }

    private void moveNode(final Node node, final String afterPageId) {

        try {
            if (StringUtils.isNotBlank(afterPageId)) {
                final Node parent = node.getParent();
                final Optional<Node> siblingNode = findPageByAncestorAndId(parent.getSession(), parent.getPath(), afterPageId);
                if (siblingNode.isEmpty()) {
                    throw new NodeNotFoundException("Sibling node " + afterPageId);
                }
                final Optional<Node> nextSibling = getNextSibling(siblingNode.get());
                if (nextSibling.isPresent()) {
                    parent.orderBefore(node.getName(), nextSibling.get().getName());
                }
                node.getSession().save();
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private Optional<Node> findPageOfSiteById(final Session session, final String siteId, final String pageId) {

        return findPageByAncestorAndId(session, JcrPaths.sites(siteId), pageId);
    }

    private Optional<Node> findPageByAncestorAndId(final Session session, final String absParentPath, final String pageId) {

        final JcrQuery query = new JcrQuery("select * from [cms:page] as p where isdescendantnode(p, :parent) and [jcr:id] = :pageId");
        query.setParam("parent", absParentPath);
        query.setParam("pageId", pageId);
        final List<Node> nodes = findByQuery(session, query);
        if (nodes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(nodes.get(0));
    }

    private Node createNewPageNode(final Node parent, final String desiredNodeName) {

        try {
            int cnt = 0;
            String nodeName = desiredNodeName;
            while (parent.hasNode(nodeName) && cnt < 10000) {
                nodeName = String.format("%s_%d", desiredNodeName, ++cnt);
            }
            return parent.addNode(nodeName, CmsType.PAGE.getName());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private String buildClonedNodeName(final Node nodeToClone) {

        try {
            final Node parent = nodeToClone.getParent();
            int cnt = 0;
            String name = String.format("Copy_of_%s", nodeToClone.getName());
            while (parent.hasNode(name) && cnt < 10000) {
                name = String.format("Copy_of_%s_%d", nodeToClone.getName(), ++cnt);
            }
            return name;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
