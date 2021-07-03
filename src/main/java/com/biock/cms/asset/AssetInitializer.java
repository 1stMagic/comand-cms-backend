package com.biock.cms.asset;

import com.biock.cms.config.CmsConfig;
import com.biock.cms.exception.RuntimeIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;

public final class AssetInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(AssetInitializer.class);

    private AssetInitializer() {

        // Empty
    }

    public static void initialize(final CmsConfig config) {

        LOG.info("{}.initialize()", AssetInitializer.class.getSimpleName());

        if (!config.getAssetsPath().toFile().exists()) {
            try {
                Files.createDirectories(config.getAssetsPath());
            } catch (final IOException e) {
                throw new RuntimeIOException(e);
            }
        }

        if (!config.getAssetsPath().toFile().isDirectory()) {
            throw new RuntimeIOException("Assets directory could not be created or already exists but is no directory");
        }

        LOG.info("Finished {}.initialize()", AssetInitializer.class.getSimpleName());
    }
}
