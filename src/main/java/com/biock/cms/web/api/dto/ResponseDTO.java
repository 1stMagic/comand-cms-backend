package com.biock.cms.web.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ResponseDTO<T> {

    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> messages;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;

    public boolean isSuccess() {

        return this.success;
    }

    public ResponseDTO<T> setSuccess(final boolean success) {

        this.success = success;
        return this;
    }

    public List<String> getMessages() {

        return this.messages;
    }

    public ResponseDTO<T> setMessages(final List<String> messages) {

        this.messages = messages;
        return this;
    }

    public ResponseDTO<T> addMessage(final String message) {

        if (StringUtils.isNotBlank(message)) {
            if (this.messages == null) {
                this.messages = new ArrayList<>();
            }
            this.messages.add(message);
        }
        return this;
    }

    public T getPayload() {

        return this.payload;
    }

    public ResponseDTO<T> setPayload(final T payload) {

        this.payload = payload;
        return this;
    }
}
