package com.biock.cms.site;

import com.biock.cms.CmsApi;
import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;
import com.biock.cms.backend.site.Navigation;
import com.biock.cms.backend.site.mapper.NavigationMapper;
import com.biock.cms.config.CmsConfig;
import com.biock.cms.exception.NodeNotFoundException;
import com.biock.cms.jcr.*;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.site.mapper.SiteMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Service
public class SiteRepository extends JcrRepository {

    private final CmsConfig config;
    private final SiteMapper siteMapper;
    private final NavigationMapper navigationMapper;

    public SiteRepository(
            final Repository repository,
            final CmsConfig config,
            final SiteMapper siteMapper,
            final NavigationMapper navigationMapper) {

        super(repository);
        this.config = config;
        this.siteMapper = siteMapper;
        this.navigationMapper = navigationMapper;
    }

    public Optional<Site> findSiteById(final String siteId) {

        try (final CloseableJcrSession session = getSession()) {
            final Optional<Node> siteNode = getSiteNode(session, siteId);
            if (siteNode.isPresent()) {
                return Optional.of(this.siteMapper.toEntity(siteNode.get()));
            }
        }
        return Optional.empty();
    }

    public Optional<List<Navigation>> getNavigation(final String siteId) {

        try (final CloseableJcrSession session = getSession()) {
            final Optional<Node> siteNode = getSiteNode(session, siteId);
            if (siteNode.isPresent()) {
                final List<Navigation> navigation = new ArrayList<>();
                processNavigationNodes(
                        getChildNodes(siteNode.get(), NodeFilters.ofType(CmsType.PAGE)),
                        navigation,
                        null,
                        null,
                        false);
                return Optional.of(navigation);
            }
            return Optional.empty();
        }
    }

    public Optional<List<Navigation>> getTopNavigation(final String siteId) {

        return getNavigation(siteId, CmsProperty.SHOW_IN_TOP_NAVIGATION, true);
    }

    public Optional<List<Navigation>> getMainNavigation(final String siteId) {

        return getNavigation(siteId, CmsProperty.SHOW_IN_MAIN_NAVIGATION, false);
    }

    public Optional<List<Navigation>> getFooterNavigation(final String siteId) {

        return getNavigation(siteId, CmsProperty.SHOW_IN_FOOTER_NAVIGATION, true);
    }

    public String getDefaultLanguageOfSite(final String siteId) {

        try (final var session = getSession()) {
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

    public Resource exportSite(final String siteId) {

        try (final var session = getSession()) {
            final Optional<Node> siteNode = getSiteNode(session, siteId);
            if (siteNode.isEmpty()) {
                throw new NodeNotFoundException("Site " + siteId);
            }
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            session.exportSystemView(siteNode.get().getPath(), baos, true, false);
            return new ByteArrayResource(baos.toByteArray());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public DateTimeFormatter getDateFormatter(final String siteId) {

        try (final var session = getSession()) {
            final Optional<Node> siteNode = getSiteNode(session, siteId);
            ZoneId timeZone = this.config.getTimeZoneId();
            String dateFormat = this.config.getDateFormat();
            if (siteNode.isPresent()) {
                timeZone = ZoneId.of(getStringProperty(siteNode.get(), CmsProperty.TIME_ZONE, this.config.getTimeZone()));
                dateFormat = getStringProperty(siteNode.get(), CmsProperty.DATE_FORMAT, this.config.getDateFormat());
            }
            return DateTimeFormatter.ofPattern(dateFormat).withZone(timeZone);
        }
    }

    public DateTimeFormatter getDateTimeFormatter(final String siteId) {

        try (final var session = getSession()) {
            final Optional<Node> siteNode = getSiteNode(session, siteId);
            ZoneId timeZone = this.config.getTimeZoneId();
            String dateFormat = this.config.getDateFormat();
            String timeFormat = this.config.getTimeFormat();
            if (siteNode.isPresent()) {
                timeZone = ZoneId.of(getStringProperty(siteNode.get(), CmsProperty.TIME_ZONE, this.config.getTimeZone()));
                dateFormat = getStringProperty(siteNode.get(), CmsProperty.DATE_FORMAT, this.config.getDateFormat());
                timeFormat = getStringProperty(siteNode.get(), CmsProperty.TIME_FORMAT, this.config.getTimeFormat());
            }
            return DateTimeFormatter.ofPattern(String.format("%s %s", dateFormat, timeFormat).trim()).withZone(timeZone);
        }
    }

    private Optional<List<Navigation>> getNavigation(
            final String siteId,
            final String navigationTypeProperty,
            final boolean flat) {

        try (final CloseableJcrSession session = getSession()) {
            final Optional<Node> siteNode = getSiteNode(session, siteId);
            if (siteNode.isPresent()) {
                final List<Navigation> navigation = new ArrayList<>();
                processNavigationNodes(
                        getChildNodes(siteNode.get(), NodeFilters.ofType(CmsType.PAGE)),
                        navigation,
                        null,
                        node -> getBooleanProperty(node, navigationTypeProperty, false),
                        flat);
                return Optional.of(navigation);
            }
            return Optional.empty();
        }
    }

    private void processNavigationNodes(
            final List<Node> nodes,
            final List<Navigation> navigation,
            final String parentId,
            final Predicate<Node> nodeFilter,
            final boolean flat) {

        try {
            for (final Node node : nodes) {
                final boolean matches = nodeFilter == null || nodeFilter.test(node);
                if (matches) {
                    navigation.add(this.navigationMapper.toEntityBuilder(node).parentId(parentId).build());
                }
                if (flat || matches) {
                    processNavigationNodes(
                            getChildNodes(node, NodeFilters.ofType(CmsType.PAGE)),
                            flat ? navigation : navigation.get(navigation.size() - 1).getMutableChildren(),
                            node.getName(),
                            nodeFilter,
                            flat);
                }
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
