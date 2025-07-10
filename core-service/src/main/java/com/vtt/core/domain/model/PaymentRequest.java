package com.vtt.core.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentRequest {

    public enum Status {
        PROCESSING, SUCCESS, ERROR
    }

    private String id;
    private String requestId;
    private String custId;
    private String serviceCode;
    private String productCode;
    private String requestPayload;
    private String channel;
    private BigDecimal amount;
    private Instant requestTime;
    private Status status;
    private String errorCode;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    public static PaymentRequest createPayment() {
        // TODO implement
        return new PaymentRequest(null, null, null, null, null, null, null, null, null, null, null, Instant.now(), Instant.now(), null, null);
    }

    public void changeStatus(Status newStatus) {
        setStatus(newStatus);
        setUpdatedAt(Instant.now());
    }
}
