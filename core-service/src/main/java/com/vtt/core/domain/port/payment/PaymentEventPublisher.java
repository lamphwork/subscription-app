package com.vtt.core.domain.port.payment;

import com.vtt.core.domain.model.PaymentRequest;

public interface PaymentEventPublisher {

    void publish(PaymentRequest paymentRequest);
}
