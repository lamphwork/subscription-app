package com.vtt.core.domain.exception;

import com.vtt.common.exception.ErrorInfo;

public enum ErrorCode implements ErrorInfo {
    SUBSCRIPTION_EXISTED("subscription already existed"),
    PAYMENT_NOTFOUND("payment not found"),
    ;
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
