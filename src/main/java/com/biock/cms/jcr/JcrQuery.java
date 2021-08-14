package com.biock.cms.jcr;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class JcrQuery {

    private final String query;
    private final Map<String, String> params;

    public JcrQuery(final String query) {

        this.query = query;
        this.params = new HashMap<>();
    }

    public JcrQuery setParam(final String name, final String value) {

        this.params.put(name, value);
        return this;
    }

    public JcrQuery setParam(final String name, final long value) {

        this.params.put(name, String.valueOf(value));
        return this;
    }

    public JcrQuery setParam(final String name, final double value) {

        this.params.put(name, String.valueOf(value));
        return this;
    }

    public String getQuery() {

        if (this.params.isEmpty()) {
            return this.query;
        }
        String preparedQuery = query;
        for (final Entry<String, String> param : this.params.entrySet()) {
            preparedQuery = preparedQuery.replace(
                    ":" + param.getKey(),
                    String.format("'%s'", escapeQueryParam(param.getValue())));
        }
        return preparedQuery;
    }

    private String escapeQueryParam(final String value) {

        return StringUtils.defaultString(value).replace("'", "''");
    }
}
