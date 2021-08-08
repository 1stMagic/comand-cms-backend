package com.biock.cms.site;

import com.biock.cms.jcr.CloseableJcrSession;
import com.biock.cms.jcr.JcrRepository;
import com.biock.cms.shared.EntityId;
import com.biock.cms.site.mapper.SiteMapper;
import org.springframework.stereotype.Service;

import javax.jcr.Node;
import javax.jcr.Repository;
import java.util.Optional;

@Service
public class SiteRepository extends JcrRepository {

    private final SiteMapper siteMapper;

    public SiteRepository(final Repository repository, final SiteMapper siteMapper) {

        super(repository);
        this.siteMapper = siteMapper;
    }

    public Optional<Site> findSiteById(final String siteId) {

        try (final CloseableJcrSession session = getSession()) {
            final Optional<Node> siteNode = getSiteNode(session, new EntityId(siteId));
            if (siteNode.isPresent()) {
                return Optional.of(this.siteMapper.toEntity(siteNode.get()));
            }
        }
        return Optional.empty();
    }
}
