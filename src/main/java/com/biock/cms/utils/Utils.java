package com.biock.cms.utils;

import java.util.Optional;

public final class Utils {

    private Utils() {

        // Empty
    }

    @SafeVarargs
    public static <T> T coalesce(final T... values) {

        for (final T value : values) {
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    @SafeVarargs
    public static <T> Optional<T> coalesce(final Optional<T>... values) {

        for (final Optional<T> value : values) {
            if (value.isPresent()) {
                return value;
            }
        }
        return Optional.empty();
    }
}
