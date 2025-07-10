package com.vtt.core.domain.port.payment.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentInput {

    private String paymentSource;
    private String business;
    private String sourceId;
    private BigDecimal amount;
    private String description;
}
