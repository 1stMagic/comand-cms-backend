package com.biock.cms.jcr;

import com.biock.cms.jcr.exception.RuntimeRepositoryException;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public final class PropertyUtils {

    private PropertyUtils() {

        // Empty
    }

    public static Property getProperty(@NotNull final Node node, @NotNull final String relPath) {

        try {
            return node.getProperty(relPath);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static String getStringProperty(@NotNull final Node node, @NotNull final String relPath) {

        return getStringProperty(getProperty(node, relPath));
    }

    public static String getStringProperty(@NotNull final Node node, @NotNull final String relPath, final String defaultValue) {

        try {
            if (node.hasProperty(relPath)) {
                return getStringProperty(getProperty(node, relPath));
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
        return defaultValue;
    }

    public static String getStringProperty(@NotNull final Property property) {

        return getStringArrayProperty(property)[0];
    }

    public static String getStringProperty(@NotNull final Value value) {

        try {
            return value.getString();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static String[] getStringArrayProperty(@NotNull final Node node, @NotNull final String relPath) {

        return getStringArrayProperty(getProperty(node, relPath));
    }

    public static String[] getStringArrayProperty(@NotNull final Property property) {

        try {
            if (property.isMultiple()) {
                return toArray(property.getValues(), PropertyUtils::getStringProperty, String[]::new);
            }
            return new String[] {getStringProperty(property.getValue())};
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static Calendar getDateProperty(@NotNull final Node node, @NotNull final String relPath) {

        return getDateProperty(getProperty(node, relPath));
    }

    public static Calendar getDateProperty(@NotNull final Property property) {

        return getDateArrayProperty(property)[0];
    }

    public static Calendar getDateProperty(@NotNull final Value value) {

        try {
            return value.getDate();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static Calendar[] getDateArrayProperty(@NotNull final Node node, @NotNull final String relPath) {

        return getDateArrayProperty(getProperty(node, relPath));
    }

    public static Calendar[] getDateArrayProperty(@NotNull final Property property) {

        try {
            if (property.isMultiple()) {
                return toArray(property.getValues(), PropertyUtils::getDateProperty, Calendar[]::new);
            }
            return new Calendar[] {getDateProperty(property.getValue())};
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static boolean getBooleanProperty(@NotNull final Node node, @NotNull final String relPath) {

        return getBooleanProperty(getProperty(node, relPath));
    }

    public static boolean getBooleanProperty(@NotNull final Node node, @NotNull final String relPath, final boolean defaultValue) {

        try {
            if (node.hasProperty(relPath)) {
                return getBooleanProperty(node, relPath);
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
        return defaultValue;
    }

    public static boolean getBooleanProperty(@NotNull final Property property) {

        try {
            if (property.isMultiple()) {
                throw new IllegalArgumentException("Cannot get boolean value from multiple property");
            }
            return getBooleanProperty(property.getValue());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static boolean getBooleanProperty(@NotNull final Value value) {

        try {
            return value.getBoolean();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static boolean[] getBooleanArrayProperty(@NotNull final Node node, @NotNull final String relPath) {

        return getBooleanArrayProperty(getProperty(node, relPath));
    }

    public static boolean[] getBooleanArrayProperty(@NotNull final Property property) {

        try {
            if (property.isMultiple()) {
                final Boolean[] values = toArray(property.getValues(), PropertyUtils::getBooleanProperty, Boolean[]::new);
                final boolean[] result = new boolean[values.length];
                System.arraycopy(values, 0, result, 0, values.length);
                return result;
            }
            return new boolean[] {getBooleanProperty(property.getValue())};
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static int getIntegerProperty(@NotNull final Node node, @NotNull final String relPath) {

        return getIntegerProperty(getProperty(node, relPath));
    }

    public static int getIntegerProperty(@NotNull final Node node, @NotNull final String relPath, final int defaultValue) {

        try {
            if (node.hasProperty(relPath)) {
                return getIntegerProperty(getProperty(node, relPath));
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
        return defaultValue;
    }

    public static int getIntegerProperty(@NotNull final Property property) {

        return getIntegerArrayProperty(property)[0];
    }

    public static int getIntegerProperty(@NotNull final Value value) {

        try {
            return (int) value.getLong();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static int[] getIntegerArrayProperty(@NotNull final Node node, @NotNull final String relPath) {

        return getIntegerArrayProperty(getProperty(node, relPath));
    }

    public static int[] getIntegerArrayProperty(@NotNull final Property property) {

        try {
            if (property.isMultiple()) {
                final Integer[] values = toArray(property.getValues(), PropertyUtils::getIntegerProperty, Integer[]::new);
                final int[] result = new int[values.length];
                System.arraycopy(values, 0, result, 0, values.length);
                return result;
            }
            return new int[] {getIntegerProperty(property.getValue())};
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private static <T> T[] toArray(
            @NotNull final Value[] values,
            @NotNull final Function<Value, T> mapper,
            @NotNull final IntFunction<T[]> generator) {

        return Stream.of(values)
                .map(mapper)
                .toArray(generator);
    }
}
