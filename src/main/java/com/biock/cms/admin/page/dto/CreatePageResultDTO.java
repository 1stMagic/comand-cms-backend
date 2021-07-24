package com.biock.cms.admin.page.dto;

import java.util.ArrayList;
import java.util.List;

public class CreatePageResultDTO {

    private boolean success;
    private List<String> messages;
    private String id;

    public boolean isSuccess() {

        return this.success;
    }

    public CreatePageResultDTO setSuccess(final boolean success) {

        this.success = success;
        return this;
    }

    public List<String> getMessages() {

        return this.messages;
    }

    public CreatePageResultDTO setMessages(final List<String> messages) {

        this.messages = messages;
        return this;
    }

    public String getId() {

        return this.id;
    }

    public CreatePageResultDTO setId(final String id) {

        this.id = id;
        return this;
    }

    public CreatePageResultDTO addMessage(final String message) {

        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
        return this;
    }
}
