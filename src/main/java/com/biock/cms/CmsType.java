package com.biock.cms;

import com.biock.cms.jcr.exception.RuntimeRepositoryException;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeType;
import javax.validation.constraints.NotNull;
import java.util.function.Predicate;

public enum CmsType {

    ROOT("cms:root"),
    SITE("cms:site"),
    PAGE("cms:page"),
    COMPONENT("cms:component"),
    LANGUAGE("cms:language");

    private final String name;

    CmsType(@NotNull final String name) {

        this.name = name;
    }

    public String getName() {

        return this.name;
    }

    public boolean isNodeType(@NotNull final Node node) {

        try {
            return isNodeType(node.getPrimaryNodeType());
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public boolean isNodeType(@NotNull final NodeType nodeType) {

        return nodeType.isNodeType(getName());
    }

    public Predicate<Node> typeFilter() {

        return this::isNodeType;
    }
}
