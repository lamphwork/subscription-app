package com.vtt.core.domain.service;

import com.vtt.common.exception.BusinessException;
import com.vtt.core.domain.exception.ErrorCode;
import com.vtt.core.domain.model.Payment;
import com.vtt.core.domain.port.payment.PaymentEventPublisher;
import com.vtt.core.domain.port.payment.PaymentHandler;
import com.vtt.core.domain.port.payment.input.CreatePaymentInput;
import com.vtt.core.domain.port.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class AsyncPaymentHandler implements PaymentHandler {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher publisher;

    @Override
    public Payment createPayment(CreatePaymentInput input) {
        Payment payment = Payment.createPayment(
                input.getBusiness(),
                input.getSourceId(),
                input.getAmount(),
                input.getDescription()
        );

        payment = paymentRepository.save(payment);
        publisher.publish(payment);
        return payment;
    }

    @Override
    public void updatePaymentStatus(String paymentId, Payment.PaymentStatus status) {
        Optional<Payment> paymentOptional = paymentRepository.findById(Long.valueOf(paymentId));
        if (paymentOptional.isEmpty()) {
            throw new BusinessException(ErrorCode.PAYMENT_NOTFOUND);
        }

        Payment payment = paymentOptional.get();
        payment.changeStatus(status);
        paymentRepository.update(payment);
    }
}
