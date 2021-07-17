package com.biock.cms.page;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class BreadcrumbTarget {

    private final String name;
    private final Map<String, String> params;

    public BreadcrumbTarget(@NotNull final String name, @NotNull final Map<String, String> params) {

        this.name = name;
        this.params = params;
    }

    public String getName() {

        return this.name;
    }

    public Map<String, String> getParams() {

        return this.params;
    }
}
