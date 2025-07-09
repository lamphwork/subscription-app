package com.vtt.core.domain.port.payment;

import com.vtt.core.domain.model.Payment;

public interface PaymentEventPublisher {

    void publish(Payment payment);
}
