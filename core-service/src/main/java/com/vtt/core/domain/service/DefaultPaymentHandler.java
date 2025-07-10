package com.vtt.core.domain.service;

import com.vtt.common.exception.BusinessException;
import com.vtt.core.domain.exception.ErrorCode;
import com.vtt.core.domain.model.PaymentRequest;
import com.vtt.core.domain.port.payment.PaymentEventPublisher;
import com.vtt.core.domain.port.payment.PaymentHandler;
import com.vtt.core.domain.port.payment.PaymentSubscriber;
import com.vtt.core.domain.port.payment.input.CreatePaymentInput;
import com.vtt.core.domain.port.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class DefaultPaymentHandler implements PaymentHandler {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher publisher;
    private final List<PaymentSubscriber> subscribers;

    @Override
    public PaymentRequest createPayment(CreatePaymentInput input) {
        PaymentRequest paymentRequest = PaymentRequest.createPayment();

        paymentRequest = paymentRepository.save(paymentRequest);
        publisher.publish(paymentRequest);
        return paymentRequest;
    }

    @Override
    public void updatePaymentStatus(String paymentId, PaymentRequest.Status status) {
        Optional<PaymentRequest> paymentOptional = paymentRepository.findById(Long.valueOf(paymentId));
        if (paymentOptional.isEmpty()) {
            throw new BusinessException(ErrorCode.PAYMENT_NOTFOUND);
        }

        PaymentRequest paymentRequest = paymentOptional.get();
        paymentRequest.changeStatus(status);
        paymentRepository.update(paymentRequest);

        subscribers.stream()
                .filter(subscriber -> subscriber.isSupport(paymentRequest))
                .forEach(subscriber -> subscriber.handleOnPaymentChange(paymentRequest));
    }
}
