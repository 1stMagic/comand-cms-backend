package com.biock.cms.jcr;

import com.biock.cms.CmsProperty;
import com.biock.cms.CmsType;

import javax.jcr.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public final class NodeFilters {

    private NodeFilters() {

        // Empty
    }

    public static Predicate<Node> onlyActive() {

        return node -> PropertyUtils.getBooleanProperty(node, CmsProperty.ACTIVE, false);
    }

    public static Predicate<Node> ofType(final CmsType type) {

        return type::isNodeType;
    }

    @SafeVarargs
    public static List<Predicate<Node>> join(final Predicate<Node> filter, final Predicate<Node>... filters) {

        final List<Predicate<Node>> joinedFilters = new ArrayList<>(Arrays.asList(filters));
        joinedFilters.add(filter);
        return joinedFilters;
    }
}
