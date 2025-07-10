package com.vtt.core.domain.port.payment;

import com.vtt.core.domain.model.PaymentRequest;

public interface PaymentSubscriber {

    boolean isSupport(PaymentRequest paymentRequest);

    void handleOnPaymentChange(PaymentRequest paymentRequest);
}
