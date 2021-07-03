package com.biock.cms.site.exception;

public class SiteException extends RuntimeException {

    public SiteException() {

        super();
    }

    public SiteException(final String message) {

        super(message);
    }

    public SiteException(final Throwable cause) {

        super(cause);
    }

    public SiteException(final String message, final Throwable cause) {

        super(message, cause);
    }
}
