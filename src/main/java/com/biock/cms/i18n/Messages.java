package com.biock.cms.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class Messages {

    private final MessageSourceAccessor i18n;

    public Messages(final MessageSource messageSource) {

        this.i18n = new MessageSourceAccessor(messageSource);
    }

    public String getMessage(final String code, final Object... args) {

        return this.i18n.getMessage(code, args);
    }

    public String getMessage(final MessageSourceResolvable resolvable) {

        if (resolvable != null && resolvable.getCodes() != null) {
            for (final String code : resolvable.getCodes()) {
                final String message = getMessage(code);
                if (message != null) {
                    return message;
                }
            }
            return resolvable.getDefaultMessage();
        }
        return null;
    }

    public Supplier<String> supplyMessage(final String code, final Object... args) {

        return () -> getMessage(code, args);
    }
}
