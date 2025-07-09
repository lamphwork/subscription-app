package com.vtt.core.domain.port.payment;

import com.vtt.core.domain.model.Payment;
import com.vtt.core.domain.port.payment.input.CreatePaymentInput;

public interface PaymentHandler {

    Payment createPayment(CreatePaymentInput input);

    void updatePaymentStatus(String paymentId, Payment.PaymentStatus status);
}
