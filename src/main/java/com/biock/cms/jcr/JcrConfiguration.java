package com.biock.cms.jcr;

import com.biock.cms.asset.AssetInitializer;
import com.biock.cms.exception.RuntimeIOException;
import com.biock.cms.config.CmsConfig;
import org.apache.jackrabbit.oak.Oak;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.segment.SegmentNodeStoreBuilders;
import org.apache.jackrabbit.oak.segment.file.FileStore;
import org.apache.jackrabbit.oak.segment.file.FileStoreBuilder;
import org.apache.jackrabbit.oak.segment.file.InvalidFileStoreVersionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.jcr.Repository;
import java.io.IOException;

@Configuration
public class JcrConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(JcrConfiguration.class);

    private static final String DEMO_SITE_NAME = "demo";

    private final CmsConfig config;

    public JcrConfiguration(final CmsConfig config) {

        this.config = config;
    }

    @PostConstruct
    public void initializeRepository() {

        LOG.info("{}.initializeRepository()", getClass().getSimpleName());

        final Repository repository = repository(oak(fileStore(this.config)));

        AssetInitializer.initialize(this.config);

        RepositoryInitializer.initialize(repository);
        RepositoryInitializer.setupSite(repository, DEMO_SITE_NAME, this.config);
    }

    @Bean
    public FileStore fileStore(final CmsConfig config) {

        LOG.info("Creating JCR file store");
        try {
            return FileStoreBuilder.fileStoreBuilder(config.getRepositoryPath().toAbsolutePath().toFile()).build();
        } catch (final IOException | InvalidFileStoreVersionException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Bean
    public Oak oak(final FileStore fileStore) {

        LOG.info("Creating OAK instance");
        return new Oak(SegmentNodeStoreBuilders.builder(fileStore).build());
    }

    @Bean
    public Repository repository(final Oak oak) {

        LOG.info("Creating JCR repository");
        return new Jcr(oak).createRepository();
    }
}
