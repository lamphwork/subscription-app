package com.vtt.adapter.domain.port.payment;

import com.vtt.common.kafka.message.PaymentResultMessage;

public interface PaymentResultPublisher {

    void publish(PaymentResultMessage paymentResultMessage);
}
