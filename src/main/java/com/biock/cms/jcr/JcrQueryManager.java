package com.biock.cms.jcr;

import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import org.apache.commons.lang3.StringUtils;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import java.util.Map;

public final class JcrQueryManager {

    private JcrQueryManager() {

        // Empty
    }

    public static String quote(final String value) {

        return StringUtils.defaultString(value).replace("'", "''");
    }

    public static Query createQuery(final Session session, final String sql) {

        try {
            return session.getWorkspace().getQueryManager().createQuery(sql, Query.JCR_SQL2);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static QueryResult executeQuery(final Session session, final String sql) {

        return executeQuery(session, sql, null);
    }

    public static QueryResult executeQuery(final Session session, final String sql, final Map<String, String> bindValues) {

        try {
            final var query = createQuery(session, bindValues(sql, bindValues));
            if (bindValues != null && !bindValues.isEmpty()) {
                bindValues.entrySet()
                        .stream()
                        .filter(entry -> !StringUtils.startsWith(entry.getKey(), "$$"))
                        .forEach(entry -> bindValue(query, entry.getKey(), JcrValue.of(session, entry.getValue())));
            }
            return query.execute();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static String bindValues(final String sql, final Map<String, String> bindValues) {

        String processedSql = sql;
        if (bindValues != null && !bindValues.isEmpty()) {
            for (final String variable : bindValues.keySet()) {
                if (StringUtils.startsWith(variable, "$$")) {
                    processedSql = processedSql.replace(variable, quote(bindValues.get(variable)));
                }
            }
        }
        return processedSql;
    }

    public static void bindValue(final Query query, final String name, final Value value) {

        try {
            query.bindValue(name, value);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
