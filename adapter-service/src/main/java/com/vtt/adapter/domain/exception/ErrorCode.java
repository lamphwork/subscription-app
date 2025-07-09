package com.vtt.adapter.domain.exception;

import com.vtt.common.exception.ErrorInfo;

public enum ErrorCode implements ErrorInfo {
    PARTNER_NOT_FOUND("partner not found");
    final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getCode() {
        return this.name();
    }

    public String getMessage() {
        return message;
    }
}
