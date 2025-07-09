package com.vtt.core.domain.port.repository;

import com.vtt.core.domain.model.Payment;

import java.util.Optional;

public interface PaymentRepository {

    Optional<Payment> findById(Long id);

    Payment save(Payment payment);

    Payment update(Payment payment);
}
