package com.biock.cms.jcr;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsType;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;

import static com.biock.cms.jcr.JcrValue.escapeQueryParam;

public final class NodeUtils {

    private NodeUtils() {

        // Empty
    }

    public static Node getSitesNode(final Session session) {

        try {
            return session.getNode(JcrPaths.sites());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static Node getConfigNode(final Session session) {

        try {
            return session.getNode(JcrPaths.config());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static Node getSiteNode(final Session session, final String siteName) {

        try {
            final var rootNode = session.getRootNode();
            final String sitePath = JcrPaths.relative(CmsNode.CMS, CmsNode.SITES, siteName);
            if (rootNode.hasNode(sitePath)) {
                return rootNode.getNode(sitePath);
            }
            return null;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static Node getNodeById(final Session session, final String id) {

        try {
            final var query = session.getWorkspace().getQueryManager().createQuery(
                    String.format("select * from [cms:page] as p where isdescendantnode(p, '/cms/sites') and [jcr:id] = '%s'", escapeQueryParam(id)),
                    Query.JCR_SQL2);
            final QueryResult result = query.execute();
            final NodeIterator nodes = result.getNodes();
            if (nodes.hasNext()) {
                return nodes.nextNode();
            }
            return null;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static Node getNodeBySiteId(final Session session, final String site, final String id) {

        try {
            final var query = session.getWorkspace().getQueryManager().createQuery(
                    String.format("select * from [cms:page] as p where isdescendantnode(p, '/cms/sites/%1$s') and [jcr:id] = '%2$s'", escapeQueryParam(site), escapeQueryParam(id)),
                    Query.JCR_SQL2);
            final QueryResult result = query.execute();
            final NodeIterator nodes = result.getNodes();
            if (nodes.hasNext()) {
                return nodes.nextNode();
            }
            return null;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static Node createIfAbsent(final Node parent, final String relPath, final CmsType nodeType) {

        return createIfAbsent(parent, relPath, nodeType.getName());
    }

    public static Node createIfAbsent(final Node parent, final String relPath, final String nodeType) {

        try {
            if (parent.hasNode(relPath)) {
                return parent.getNode(relPath);
            }
            return parent.addNode(relPath, nodeType);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
