package com.biock.cms.shared;

import java.util.List;

public class ValidationErrorDTO {

    private String objectName;
    private String field;
    private String rejectedValue;
    private List<String> messageCodes;
    private String defaultMessage;

    public String getObjectName() {

        return this.objectName;
    }

    public ValidationErrorDTO setObjectName(final String objectName) {

        this.objectName = objectName;
        return this;
    }

    public String getField() {

        return this.field;
    }

    public ValidationErrorDTO setField(final String field) {

        this.field = field;
        return this;
    }

    public String getRejectedValue() {

        return this.rejectedValue;
    }

    public ValidationErrorDTO setRejectedValue(final String rejectedValue) {

        this.rejectedValue = rejectedValue;
        return this;
    }

    public List<String> getMessageCodes() {

        return this.messageCodes;
    }

    public ValidationErrorDTO setMessageCodes(final List<String> messageCodes) {

        this.messageCodes = messageCodes;
        return this;
    }

    public String getDefaultMessage() {

        return this.defaultMessage;
    }

    public ValidationErrorDTO setDefaultMessage(final String defaultMessage) {

        this.defaultMessage = defaultMessage;
        return this;
    }
}
