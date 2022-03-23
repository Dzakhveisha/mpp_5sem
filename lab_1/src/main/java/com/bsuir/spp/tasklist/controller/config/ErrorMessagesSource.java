package com.bsuir.spp.tasklist.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ErrorMessagesSource {

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    ErrorMessagesSource(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String msgCode, Object... params) {
        return messageSource.getMessage(msgCode, params, Locale.ENGLISH);
    }
}
