package com.vtt.core.infrastructure.jpa.impl;

import com.vtt.core.domain.model.PaymentRequest;
import com.vtt.core.domain.port.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FakePaymentRepository implements PaymentRepository {

    @Override
    public Optional<PaymentRequest> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public PaymentRequest save(PaymentRequest paymentRequest) {
        return null;
    }

    @Override
    public PaymentRequest update(PaymentRequest paymentRequest) {
        return null;
    }
}
