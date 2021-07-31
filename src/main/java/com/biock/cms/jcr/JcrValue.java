package com.biock.cms.jcr;

import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import org.apache.commons.lang3.StringUtils;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

public final class JcrValue {

    private JcrValue() {

        // Empty
    }

    public static Value of(final Session session, final String value) {

        try {
            return session.getValueFactory().createValue(value);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public static String escapeQueryParam(final String value) {

        return StringUtils.defaultString(value).replace("'", "''");
    }
}
