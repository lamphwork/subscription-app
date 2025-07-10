package com.vtt.core.domain.port.usecase;


import com.vtt.core.domain.model.CustPayment;
import com.vtt.core.domain.port.usecase.params.CreateCustPaymentInput;
import com.vtt.core.domain.port.usecase.params.CheckSubscriptionInput;
import com.vtt.core.domain.port.usecase.params.RenewSubscriptionInput;

public interface CustPaymentUseCase {

    String createCustPayment(CreateCustPaymentInput input);

    void reNewSubscription(RenewSubscriptionInput input);

    CustPayment checkSubscription(CheckSubscriptionInput input);
}
