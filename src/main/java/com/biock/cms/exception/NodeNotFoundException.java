package com.biock.cms.exception;

public class NodeNotFoundException extends RuntimeException {

    public NodeNotFoundException() {

        super();
    }

    public NodeNotFoundException(final String message) {

        super(message);
    }

    public NodeNotFoundException(final Throwable cause) {

        super(cause);
    }

    public NodeNotFoundException(final String message, final Throwable cause) {

        super(message, cause);
    }
}
