package com.vtt.common.exception;

import lombok.AllArgsConstructor;

public class BusinessException extends RuntimeException {
    protected String errorCode;
    protected String errorMsg;

    public BusinessException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(ErrorInfo errorInfo) {
        this(errorInfo.getCode(), errorInfo.getMessage());
    }

}
