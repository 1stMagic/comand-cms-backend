package com.biock.cms.page;

import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;
import com.biock.cms.jcr.CloseableJcrSession;
import com.biock.cms.jcr.NodeUtils;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.site.Site;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.biock.cms.jcr.NodeUtils.getSiteNode;
import static com.biock.cms.jcr.NodeUtils.getSitesNode;
import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;

@org.springframework.stereotype.Repository
public class PageRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PageRepository.class);

    private final Repository repository;
    private final PageMapper pageMapper;

    public PageRepository(final Repository repository, final PageMapper pageMapper) {

        this.repository = repository;
        this.pageMapper = pageMapper;
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
