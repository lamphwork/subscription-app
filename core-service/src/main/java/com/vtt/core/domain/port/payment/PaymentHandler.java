package com.vtt.core.domain.port.payment;

import com.vtt.core.domain.model.PaymentRequest;
import com.vtt.core.domain.port.payment.input.CreatePaymentInput;

public interface PaymentHandler {

    PaymentRequest createPayment(CreatePaymentInput input);

    void updatePaymentStatus(String paymentId, PaymentRequest.Status status);
}
