package com.biock.cms.page;

public class BreadcrumbItem {

    private final String name;
    private final String path;

    public BreadcrumbItem(final String name, final String path) {

        this.name = name;
        this.path = path;
    }

    public String getName() {

        return this.name;
    }

    public String getPath() {

        return this.path;
    }
}
