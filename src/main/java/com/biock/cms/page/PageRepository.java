package com.biock.cms.page;

import com.biock.cms.CmsType;
import com.biock.cms.jcr.JcrPaths;
import com.biock.cms.jcr.JcrRepository;
import com.biock.cms.page.mapper.PageMapper;
import com.biock.cms.shared.EntityId;
import com.biock.cms.site.Site;
import org.springframework.stereotype.Service;

import javax.jcr.Node;
import javax.jcr.Repository;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.biock.cms.jcr.NodeFilters.join;
import static com.biock.cms.jcr.NodeFilters.ofType;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class PageRepository extends JcrRepository {

    private final PageMapper pageMapper;

    public PageRepository(final Repository repository, final PageMapper pageMapper) {

        super(repository);
        this.pageMapper = pageMapper;
    }

    public Optional<Page> findPageBySIteIdAndRelativePagePath(final String siteId, final String relativePagePath) {

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
    public final List<Page> getPagesOfSite(final EntityId siteId, final Predicate<Node>... nodeFilters) {

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
}
