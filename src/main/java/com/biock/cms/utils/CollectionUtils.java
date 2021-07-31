package com.biock.cms.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CollectionUtils {

    private CollectionUtils() {

        // Empty
    }

    public static <T> List<T> copy(final List<T> list) {

        if (list == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(list);
    }

    public static <K, V>Map<K, V> copy(final Map<K, V> map) {

        if (map == null) {
            return new HashMap<>();
        }
        return new HashMap<>(map);
    }
}
