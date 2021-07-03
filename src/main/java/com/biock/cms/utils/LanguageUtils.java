package com.biock.cms.utils;

import com.biock.cms.jcr.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;

public final class LanguageUtils {

    private static final Logger LOG = LoggerFactory.getLogger(LanguageUtils.class);

    private LanguageUtils() {

        // Empty
    }

    public static String getLanguageNodePropertyValue(@NotNull final Node node, @NotNull final String language, @NotNull final String propertyName) {

        return getLanguageNodePropertyValue(node, language, propertyName, String.format("[%s:%s]", language, propertyName));
    }

    public static String getLanguageNodePropertyValue(@NotNull final Node node, @NotNull final String language, @NotNull final String propertyName, String defaultValue) {

        try {
            return PropertyUtils.getStringProperty(node.getNode(language), propertyName, defaultValue);
        } catch (final RepositoryException e) {
            LOG.debug("Error getting property '{}' or language node '{}': {}", propertyName, language, e.getMessage(), e);
        }

        return defaultValue;
    }
}
