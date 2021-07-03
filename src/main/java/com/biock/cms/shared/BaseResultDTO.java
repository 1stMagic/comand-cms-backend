package com.biock.cms.shared;

public class BaseResultDTO<T> {

    private boolean success;
    private String message;

    public boolean isSuccess() {

        return this.success;
    }

    public T setSuccess(final boolean success) {

        this.success = success;
        //noinspection unchecked
        return (T) this;
    }

    public String getMessage() {

        return this.message;
    }

    public T setMessage(final String message) {

        this.message = message;
        //noinspection unchecked
        return (T) this;
    }
}
