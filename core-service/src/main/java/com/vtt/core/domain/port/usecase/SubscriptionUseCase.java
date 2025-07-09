package com.vtt.core.domain.port.usecase;


import com.vtt.core.domain.model.Subscription;
import com.vtt.core.domain.port.usecase.params.AddSubscriptionInput;
import com.vtt.core.domain.port.usecase.params.CheckSubscriptionInput;

public interface SubscriptionUseCase {

    void addSubscription(AddSubscriptionInput input);

    void reNewSubscription(Subscription subscription);

    Subscription checkSubscription(CheckSubscriptionInput input);
}
