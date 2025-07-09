package com.vtt.core.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment {

    public enum PaymentStatus {
        PROCESSING, SUCCESS, ERROR
    }

    private Long id;
    private String referenceId;
    private String paymentSource;
    private String description;
    private BigDecimal amount;
    private String business;
    private String sourceId;
    private PaymentStatus status;
    private OffsetDateTime createTime;
    private OffsetDateTime updateTime;

    public static Payment createPayment(String business, String sourceId, BigDecimal amount, String description) {
        return new Payment(null, null, description, amount, business, sourceId, PaymentStatus.PROCESSING, OffsetDateTime.now(), null);
    }

    public void changeStatus(PaymentStatus newStatus) {
        setStatus(newStatus);
        setUpdateTime(OffsetDateTime.now());
    }
}
