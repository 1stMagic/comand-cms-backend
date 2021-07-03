package com.biock.cms.jcr.exception;

public class RuntimeSAXException extends RuntimeException {

    public RuntimeSAXException() {

        super();
    }

    public RuntimeSAXException(final String message) {

        super(message);
    }

    public RuntimeSAXException(final String message, final Throwable cause) {

        super(message, cause);
    }

    public RuntimeSAXException(final Throwable cause) {

        super(cause);
    }
}
