package com.vtt.core.domain.port.repository;

import com.vtt.core.domain.model.PaymentRequest;

import java.util.Optional;

public interface PaymentRepository {

    Optional<PaymentRequest> findById(Long id);

    PaymentRequest save(PaymentRequest paymentRequest);

    PaymentRequest update(PaymentRequest paymentRequest);
}
