package com.biock.cms.jcr;

import com.biock.cms.CmsApi;
import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.EntityId;

import javax.jcr.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

public class JcrRepository {

    private final Repository repository;

    public JcrRepository(final Repository repository) {

        this.repository = repository;
    }

    public CloseableJcrSession getSession() {

        return CloseableJcrSession.adminSession(this.repository);
    }

    public Optional<Node> getSiteNode(final Session session, final EntityId siteId) {

        try {
            final var rootNode = session.getRootNode();
            final String sitePath = JcrPaths.relative(CmsNode.CMS, CmsNode.SITES, siteId.getId());
            if (rootNode.hasNode(sitePath)) {
                return Optional.of(rootNode.getNode(sitePath));
            }
            return Optional.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public String getDefaultLanguageOfSite(final String siteId) {

        try (final CloseableJcrSession session = getSession()) {
            final String languagesPath = JcrPaths.absolute(CmsNode.CMS, CmsNode.SITES, siteId, CmsNode.LANGUAGES);
            if (session.hasNode(languagesPath)) {
                final NodeIterator languageNodes = session.getNode(languagesPath).getNodes();
                while (languageNodes.hasNext()) {
                    final Node languageNode = languageNodes.nextNode();
                    if (CmsType.LANGUAGE.isNodeType(languageNode)
                            && getBooleanProperty(languageNode, CmsProperty.ACTIVE, false)
                            && getBooleanProperty(languageNode, CmsProperty.DEFAULT_LANGUAGE, false)) {
                        return getStringProperty(languageNode, CmsProperty.ISO_639_1_CODE);
                    }
                }
            }
            return CmsApi.DEFAULT_LANGUAGE;
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
}
