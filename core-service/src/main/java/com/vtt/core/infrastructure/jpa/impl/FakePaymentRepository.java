package com.vtt.core.infrastructure.jpa.impl;

import com.vtt.core.domain.model.Payment;
import com.vtt.core.domain.port.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FakePaymentRepository implements PaymentRepository {

    @Override
    public Optional<Payment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Payment save(Payment payment) {
        return null;
    }

    @Override
    public Payment update(Payment payment) {
        return null;
    }
}
