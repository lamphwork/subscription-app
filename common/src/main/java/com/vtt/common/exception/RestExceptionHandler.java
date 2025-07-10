package com.vtt.common.exception;

import com.vtt.common.http.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@ConditionalOnWebApplication
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code(e.getErrorCode())
                        .message(e.getErrorMsg())
                        .build()
        );
    }

    @ExceptionHandler(InfraException.class)
    public ResponseEntity<ApiResponse<Object>> handleInfraException(InfraException e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code("Unknown error")
                        .message("Unknown error")
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleOtherException(Exception e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code("Unknown error")
                        .message("Unknown error")
                        .build()
        );
    }
}
