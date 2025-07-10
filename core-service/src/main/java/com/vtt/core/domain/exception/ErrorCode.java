package com.vtt.core.domain.exception;

import com.vtt.common.exception.ErrorInfo;
import lombok.Getter;

@Getter
public enum ErrorCode implements ErrorInfo {
    SUBSCRIPTION_EXISTED("subscription already existed"),
    SUBSCRIPTION_NOTFOUND("subscription not found"),
    SUBSCRIPTION_INACTIVE("subscription inactive"),
    PAYMENT_NOTFOUND("payment not found"),
    ;
    final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getCode() {
        return this.name();
    }

}
