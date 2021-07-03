package com.biock.cms.jcr.exception;

public class RuntimeRepositoryException extends RuntimeException {

    public RuntimeRepositoryException() {

        super();
    }

    public RuntimeRepositoryException(final String message) {

        super(message);
    }

    public RuntimeRepositoryException(final String message, final Throwable cause) {

        super(message, cause);
    }

    public RuntimeRepositoryException(final Throwable cause) {

        super(cause);
    }
}
