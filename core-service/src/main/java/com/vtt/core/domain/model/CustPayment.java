package com.vtt.core.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
/**
 * Thông tin đăng ký thanh toán tự động của thuê bao
 */
public class CustPayment {

    private String id;

    private String custId;

    private String productCode;

    private boolean autoRenew;

    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;

    public static CustPayment create(String custId, String productCode, boolean autoRenew, String actionUser) {
        return new CustPayment(
                UUID.randomUUID().toString(),
                custId,
                productCode,
                autoRenew,
                Instant.now(),
                null,
                actionUser,
                null
        );
    }

}
