package com.biock.cms.site;

import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;
import com.biock.cms.config.CmsConfig;
import com.biock.cms.jcr.CloseableJcrSession;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.biock.cms.jcr.NodeUtils.createIfAbsent;
import static com.biock.cms.jcr.NodeUtils.getSitesNode;
import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;

@org.springframework.stereotype.Repository
public class SiteRepository {

    private final CmsConfig config;
    private final Repository repository;

    public SiteRepository(final CmsConfig config, final Repository repository) {

        this.config = config;
        this.repository = repository;
    }

    public boolean hasSite(@NotNull final String name) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            return getSitesNode(session).hasNode(JcrPaths.relative(name));
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public Optional<Site> getSite(@NotNull final String name, final boolean onlyActive) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final var sites = getSitesNode(session);
            final String relPath = JcrPaths.relative(name);
            if (sites.hasNode(relPath)) {
                final Site site = createSiteMapper().toEntity(sites.getNode(relPath));
                if (site.isActive() || !onlyActive) {
                    return Optional.of(site);
                }
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
        return Optional.empty();
    }

    public List<Site> getAllSites(final boolean onlyActive) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final List<Site> sites = new ArrayList<>();
            final SiteMapper siteMapper = createSiteMapper();
            final var nodeIterator = getSitesNode(session).getNodes();
            while (nodeIterator.hasNext()) {
                final var node = nodeIterator.nextNode();
                if (!onlyActive || getBooleanProperty(node, CmsProperty.ACTIVE)) {
                    sites.add(siteMapper.toEntity(node));
                }
            }
            return sites;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public Site save(@NotNull final Site site) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            createSiteMapper().toNode(
                    site,
                    createIfAbsent(
                            getSitesNode(session),
                            JcrPaths.relative(site.getDescriptor().getName()),
                            CmsType.SITE));
            session.save();
        }

        return site;
    }

    private SiteMapper createSiteMapper() {

        return new SiteMapper(this.config.getTimeZoneOffset());
    }
}
