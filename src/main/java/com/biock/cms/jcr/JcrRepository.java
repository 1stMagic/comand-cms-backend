package com.biock.cms.jcr;

import com.biock.cms.CmsNode;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class JcrRepository {

    private final Repository repository;

    public JcrRepository(final Repository repository) {

        this.repository = repository;
    }

    public CloseableJcrSession getSession() {

        return CloseableJcrSession.adminSession(this.repository);
    }

    public Optional<Node> getSiteNode(final Session session, final String siteId) {

        try {
            final var rootNode = session.getRootNode();
            final String sitePath = JcrPaths.relative(CmsNode.CMS, CmsNode.SITES, siteId);
            if (rootNode.hasNode(sitePath)) {
                return Optional.of(rootNode.getNode(sitePath));
            }
            return Optional.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @SafeVarargs
    public final List<Node> getChildNodes(final Node parent, final Predicate<Node>... nodeFilters) {

        return getChildNodes(parent, Arrays.asList(nodeFilters));
    }

    public List<Node> getChildNodes(final Node parent, final List<Predicate<Node>> nodeFilters) {

        try {
            final List<Node> childNodes = new ArrayList<>();
            final Predicate<Node> filter = node -> nodeFilters.isEmpty()
                    || nodeFilters.stream().allMatch(nodeFilter -> nodeFilter.test(node));
            final NodeIterator nodes = parent.getNodes();
            while (nodes.hasNext()) {
                final Node node = nodes.nextNode();
                if (filter.test(node)) {
                    childNodes.add(node);
                }
            }
            return childNodes;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public List<Node> findByQuery(final Session session, final JcrQuery query) {

        try {
            final List<Node> nodes = new ArrayList<>();
            final QueryResult result = session.getWorkspace()
                    .getQueryManager()
                    .createQuery(query.getQuery(), Query.JCR_SQL2)
                    .execute();
            final NodeIterator nodeIterator = result.getNodes();
            if (nodeIterator.hasNext()) {
                nodes.add(nodeIterator.nextNode());
            }
            return nodes;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public Optional<Node> getNextSibling(final Node node) {

        try {
            final NodeIterator nodes = node.getParent().getNodes();
            while (nodes.hasNext()) {
                final Node siblingNode = nodes.nextNode();
                if (siblingNode.isSame(node) && nodes.hasNext()) {
                    return Optional.of(nodes.nextNode());
                }
            }
            return Optional.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
