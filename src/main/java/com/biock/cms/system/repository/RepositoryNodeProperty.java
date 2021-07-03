package com.biock.cms.system.repository;

public class RepositoryNodeProperty {

    private int type;
    private String name;
    private String value;
    private boolean isMultiple;

    public int getType() {

        return this.type;
    }

    public RepositoryNodeProperty setType(final int type) {

        this.type = type;
        return this;
    }

    public String getName() {

        return this.name;
    }

    public RepositoryNodeProperty setName(final String name) {

        this.name = name;
        return this;
    }

    public String getValue() {

        return this.value;
    }

    public RepositoryNodeProperty setValue(final String value) {

        this.value = value;
        return this;
    }

    public boolean isMultiple() {

        return this.isMultiple;
    }

    public RepositoryNodeProperty setMultiple(final boolean multiple) {

        isMultiple = multiple;
        return this;
    }
}
