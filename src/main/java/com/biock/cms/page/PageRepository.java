package com.biock.cms.page;

import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;
import com.biock.cms.jcr.CloseableJcrSession;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.site.Site;
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

        try (final CloseableJcrSession session = CloseableJcrSession.adminSession(this.repository)) {
            final Node siteNode = getSiteNode(session, siteName);
            if (siteNode != null
                    && (!onlyActive || getBooleanProperty(siteNode, CmsProperty.ACTIVE))
                    && siteNode.hasNode(relativePagePath)) {
                final Node pageNode = siteNode.getNode(relativePagePath);
                if (!onlyActive || getBooleanProperty(pageNode, CmsProperty.ACTIVE)) {
                    return Optional.of(this.pageMapper.toEntity(pageNode));
                }
            }
            return Optional.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public List<Page> getPages(@NotNull final Site site, final boolean onlyActive) {

        try (final CloseableJcrSession session = CloseableJcrSession.adminSession(this.repository)) {
            return getPages(getSitesNode(session).getNode(site.getName()).getNodes(), onlyActive);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public List<Page> getPages(@NotNull final Page page, final boolean onlyActive) {

        try (final CloseableJcrSession session = CloseableJcrSession.adminSession(this.repository)) {
            return getPages(session.getNode(page.getPath()).getNodes(), onlyActive);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private List<Page> getPages(@NotNull final NodeIterator pageNodeIterator, final boolean onlyActive) {

        try {
            final List<Page> result = new ArrayList<>();
            while (pageNodeIterator.hasNext()) {
                final Node pageNode = pageNodeIterator.nextNode();
                if (pageNode.getPrimaryNodeType().isNodeType(CmsType.PAGE)
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
