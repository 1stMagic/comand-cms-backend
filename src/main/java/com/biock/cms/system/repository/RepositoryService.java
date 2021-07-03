package com.biock.cms.system.repository;

import com.biock.cms.jcr.CloseableJcrSession;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.jcr.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@Service
public class RepositoryService {

    private final Repository repository;

    public RepositoryService(final Repository repository) {

        this.repository = repository;
    }

    public List<RepositoryNode> getNodes(final String path) {

        final UnaryOperator<String> toAbsolutePath = p -> p.startsWith("/") ? p : String.format("/%s", p);
        try (final var session = CloseableJcrSession.adminSession(this.repository)) {
            return getChildNodes(StringUtils.isBlank(path) ? session.getRootNode() : session.getNode(toAbsolutePath.apply(path)));
        }
    }

    private List<RepositoryNode> getChildNodes(final Node parent) {

        try {
            final List<RepositoryNode> childNodes = new ArrayList<>();
            final NodeIterator nodes = parent.getNodes();
            while (nodes.hasNext()) {
                final var node = nodes.nextNode();
                final PropertyIterator properties = node.getProperties();
                final var childNode = new RepositoryNode(node.getPrimaryNodeType().getName(), node.getName());
                while (properties.hasNext()) {
                    final var property = properties.nextProperty();
                    childNode.addProperty(new RepositoryNodeProperty()
                            .setType(property.getType())
                            .setName(property.getName())
                            .setValue(propertyValueToString(property))
                            .setMultiple(property.isMultiple()));
                }
                childNodes.add(childNode);
            }
            return childNodes;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    private String propertyValueToString(final Property property) {

        try {
            if (property.isMultiple()) {
                final Function<Value, String> toString = value -> {
                    try {
                        return value.getString();
                    } catch (final RepositoryException e) {
                        throw new RuntimeRepositoryException(e);
                    }
                };
                return "[" + Stream.of(property.getValues()).map(toString).collect(joining(", ")) + "]";
            }
            return property.getString();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
