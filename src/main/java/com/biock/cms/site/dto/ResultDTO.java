package com.biock.cms.site.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ResultDTO<T extends ResultDTO<?>> {

    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> messages;

    public boolean isSuccess() {

        return this.success;
    }

    public T setSuccess(final boolean success) {

        this.success = success;
        return (T) this;
    }

    public List<String> getMessages() {

        return this.messages;
    }

    public T setMessages(final List<String> messages) {

        this.messages = messages;
        return (T) this;
    }

    public T addMessage(final String message) {

        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
        return (T) this;
    }
}
