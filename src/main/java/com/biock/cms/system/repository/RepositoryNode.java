package com.biock.cms.system.repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoryNode {

    private String primaryType;
    private String name;
    private List<RepositoryNodeProperty> properties;

    public RepositoryNode(final String primaryType, final String name) {

        this.primaryType = primaryType;
        this.name = name;
        this.properties = new ArrayList<>();
    }

    public String getPrimaryType() {

        return this.primaryType;
    }

    public RepositoryNode setPrimaryType(final String primaryType) {

        this.primaryType = primaryType;
        return this;
    }

    public String getName() {

        return this.name;
    }

    public RepositoryNode setName(final String name) {

        this.name = name;
        return this;
    }

    public List<RepositoryNodeProperty> getProperties() {

        return this.properties;
    }

    public RepositoryNode setProperties(final List<RepositoryNodeProperty> properties) {

        this.properties.clear();
        if (properties != null) {
            this.properties.addAll(properties);
        }
        return this;
    }

    public RepositoryNode addProperty(final RepositoryNodeProperty property) {

        this.properties.add(property);
        return this;
    }
}
