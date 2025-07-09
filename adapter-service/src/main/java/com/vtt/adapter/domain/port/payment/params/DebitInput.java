package com.vtt.adapter.domain.port.payment.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitInput {

    private String paymentId;
    private String partnerCode;
    private String business;
    private BigDecimal amount;
    private String description;
}
