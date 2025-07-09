package com.vtt.common.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResultMessage {

    private String paymentId;
    private String business;
    private String refId;
    private boolean success;

}
