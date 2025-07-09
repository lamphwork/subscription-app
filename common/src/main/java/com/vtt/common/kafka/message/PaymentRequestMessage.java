package com.vtt.common.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestMessage {

    private String paymentId;
    private String paymentSource;
    private String description;
    private BigDecimal amount;
    private String business;
    private String relationId;
    private OffsetDateTime sendTime;

}
