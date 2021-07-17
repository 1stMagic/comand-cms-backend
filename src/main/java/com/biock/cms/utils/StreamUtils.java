package com.biock.cms.utils;

import com.biock.cms.jcr.exception.RuntimeRepositoryException;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class StreamUtils {

    private StreamUtils() {

        // Empty
    }

    public static Stream<Node> streamChildren(@NotNull final Node node) {

        try {
            return stream(node.getNodes());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static Stream<Node> stream(@NotNull final NodeIterator iterator) {

        //noinspection unchecked
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize((Iterator<Node>) iterator, Spliterator.ORDERED),
                false);
    }
}
