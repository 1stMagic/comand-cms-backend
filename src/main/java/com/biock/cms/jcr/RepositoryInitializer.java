package com.biock.cms.jcr;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsType;
import com.biock.cms.config.CmsConfig;
import com.biock.cms.exception.RuntimeIOException;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeManager;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.biock.cms.jcr.NodeUtils.createIfAbsent;
import static java.util.Objects.requireNonNull;

public final class RepositoryInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(RepositoryInitializer.class);

    private RepositoryInitializer() {

        // Empty
    }

    public static void initialize(final Repository repository) {

        LOG.info("{}.initialize()", RepositoryInitializer.class.getSimpleName());

        try (final var session = CloseableJcrSession.adminSession(repository)) {
            setupNamespaces(session);
            setupTypes(session);
            setupRepository(session);
        }

        LOG.info("Finished {}.initialize()", RepositoryInitializer.class.getSimpleName());
    }

    public static void setupSite(final Repository repository, final String demoSiteName, final CmsConfig config) {

        LOG.info("{}.setupSite({})", RepositoryInitializer.class.getSimpleName(), demoSiteName);

        setupSiteRepository(repository, demoSiteName);
        setupSiteAssets(config, demoSiteName);

        LOG.info("Finished importing Demo site");
    }

    private static void setupSiteRepository(final Repository repository, final String demoSiteName) {

        try (final var session = CloseableJcrSession.adminSession(repository)) {
            if (session.hasNode(JcrPaths.sites(demoSiteName))) {
                return;
            }
            final var resource = String.format("/setup/site/%s.xml", demoSiteName);
            LOG.info("Importing site data from resource '{}'", resource);
            try (final InputStream stream = RepositoryInitializer.class.getResourceAsStream(resource)) {
                session.importXML(JcrPaths.sites(), stream, ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW);
                session.save();
            } catch (final IOException e) {
                throw new RuntimeIOException(e);
            }
        }
    }

    private static void setupSiteAssets(final CmsConfig config, final String demoSiteName) {

        if (!config.getAssetsPath().toFile().isDirectory()) {
            LOG.warn("Unable to setup assets for site '{}' because assets directory '{}' doesn't exist", demoSiteName, config.getAssetsPath().toAbsolutePath());
            return;
        }

        if (!config.getAssetsPath().resolve(demoSiteName).toFile().exists()) {
            final var resource = String.format("/setup/site/%s-assets.zip", demoSiteName);
            LOG.info("Setting up site assets from resource '{}'", resource);
            try (final var zip = new ZipInputStream(requireNonNull(RepositoryInitializer.class.getResourceAsStream(resource)))) {
                for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                    if (entry.isDirectory()) {
                        LOG.info("Create directory '{}'", config.getAssetsPath().resolve(entry.getName()));
                        Files.createDirectories(config.getAssetsPath().resolve(entry.getName()));
                    } else {
                        LOG.info("Create file '{}'", config.getAssetsPath().resolve(entry.getName()));
                        Files.copy(zip, config.getAssetsPath().resolve(entry.getName()));
                    }
                }
            } catch (final IOException e) {
                throw new RuntimeIOException(e);
            }
        }
    }

    private static void setupNamespaces(final Session session) {

        LOG.info("{}.setupNamespaces()", RepositoryInitializer.class.getSimpleName());

        try {
            final var workspace = session.getWorkspace();
            final var namespaceRegistry = workspace.getNamespaceRegistry();
            final String[] namespacePrefixes = namespaceRegistry.getPrefixes();

            if (namespacePrefixes.length == 0 || !Arrays.asList(namespacePrefixes).contains("cms")) {
                namespaceRegistry.registerNamespace("cms", "https://www.biock.com/cms/1.0");
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private static void setupTypes(final Session session) {

        LOG.info("{}.setupTypes()", RepositoryInitializer.class.getSimpleName());

        try {
            final var workspace = session.getWorkspace();
            final var nodeTypeManager = workspace.getNodeTypeManager();
            createType(nodeTypeManager, CmsType.ROOT);
            createType(nodeTypeManager, CmsType.SITE);
            createType(nodeTypeManager, CmsType.PAGE);
            createType(nodeTypeManager, CmsType.COMPONENT);
            createType(nodeTypeManager, CmsType.LANGUAGE);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private static void setupRepository(final Session session) {

        LOG.info("{}.setupRepository()", RepositoryInitializer.class.getSimpleName());

        try {
            final Node cms = createIfAbsent(session.getRootNode(), CmsNode.CMS, CmsType.ROOT);
            createIfAbsent(cms, CmsNode.CONFIG, NodeType.NT_UNSTRUCTURED);
            createIfAbsent(cms, CmsNode.SITES, NodeType.NT_UNSTRUCTURED);
            session.save();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private static void createType(final NodeTypeManager nodeTypeManager, final CmsType type) {

        LOG.info("{}.createType(name = {})", RepositoryInitializer.class.getSimpleName(), type.getName());

        try {
            if (!nodeTypeManager.hasNodeType(type.getName())) {
                final var nodeTypeTemplate = nodeTypeManager.createNodeTypeTemplate();
                nodeTypeTemplate.setName(type.getName());
                nodeTypeTemplate.setDeclaredSuperTypeNames(new String[]{NodeType.NT_UNSTRUCTURED});
                nodeTypeTemplate.setOrderableChildNodes(true);
                nodeTypeTemplate.setQueryable(true);
                nodeTypeManager.registerNodeType(nodeTypeTemplate, false);
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
