package com.biock.cms.page;

import com.biock.cms.shared.Label;

import java.util.List;

public class Breadcrumbs {

    private final Label label;
    private final String separator;
    private final List<BreadcrumbItem> items;

    public Breadcrumbs(final Label label, final String separator, final List<BreadcrumbItem> items) {

        this.label = label;

        this.separator = separator;
        this.items = items;
    }
}
