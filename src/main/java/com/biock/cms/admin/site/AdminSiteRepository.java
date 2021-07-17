package com.biock.cms.admin.site;

import com.biock.cms.config.CmsConfig;
import com.biock.cms.jcr.CloseableJcrSession;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;
import java.util.Optional;

import static com.biock.cms.jcr.NodeUtils.getSitesNode;

@org.springframework.stereotype.Repository
public class AdminSiteRepository {

    private final CmsConfig config;
    private final Repository repository;

    public AdminSiteRepository(final CmsConfig config, final Repository repository) {

        this.config = config;
        this.repository = repository;
    }

    public Optional<AdminSite> getSite(@NotNull final String name) {

        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            final var sites = getSitesNode(session);
            final String relPath = JcrPaths.relative(name);
            if (sites.hasNode(relPath)) {
                final AdminSite site = createSiteMapper().toEntity(sites.getNode(relPath));
                return Optional.of(site);
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }

        return Optional.empty();
    }

    private AdminSiteMapper createSiteMapper() {

        return new AdminSiteMapper(this.config.getTimeZoneOffset());
    }
}
