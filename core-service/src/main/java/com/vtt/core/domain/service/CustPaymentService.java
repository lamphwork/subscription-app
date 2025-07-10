package com.vtt.core.domain.service;

import com.vtt.core.domain.model.CustPayment;
import com.vtt.core.domain.model.PaymentRequest;
import com.vtt.core.domain.port.payment.PaymentSubscriber;
import com.vtt.core.domain.port.repository.CustPaymentRepository;
import com.vtt.core.domain.port.usecase.CustPaymentUseCase;
import com.vtt.core.domain.port.usecase.params.CreateCustPaymentInput;
import com.vtt.core.domain.port.usecase.params.CheckSubscriptionInput;
import com.vtt.core.domain.port.usecase.params.RenewSubscriptionInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustPaymentService implements CustPaymentUseCase, PaymentSubscriber {

    private final CustPaymentRepository custPaymentRepository;

    @Override
    public String createCustPayment(CreateCustPaymentInput input) {
        CustPayment custPayment = CustPayment.create(
                input.getCustId(),
                input.getProductCode(),
                input.getAutoRenew(),
                "" // TODO replace by token user
        );

        return custPaymentRepository.save(custPayment).getId();
    }

    @Override
    public void reNewSubscription(RenewSubscriptionInput input) {
        // TODO implement
    }

    @Override
    public CustPayment checkSubscription(CheckSubscriptionInput input) {
        // TODO implement
        return null;
    }

    @Override
    public boolean isSupport(PaymentRequest paymentRequest) {
        return false;
    }

    @Override
    public void handleOnPaymentChange(PaymentRequest paymentRequest) {
        // TODO implement when payment status change
    }
}
