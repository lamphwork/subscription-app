package com.vtt.core.domain.service;

import com.vtt.common.exception.BusinessException;
import com.vtt.core.domain.constant.Business;
import com.vtt.core.domain.exception.ErrorCode;
import com.vtt.core.domain.model.Subscription;
import com.vtt.core.domain.port.payment.input.CreatePaymentInput;
import com.vtt.core.domain.port.repository.SubscriptionRepository;
import com.vtt.core.domain.port.usecase.SubscriptionUseCase;
import com.vtt.core.domain.port.usecase.params.AddSubscriptionInput;
import com.vtt.core.domain.port.usecase.params.CheckSubscriptionInput;
import com.vtt.core.domain.port.payment.PaymentHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionService implements SubscriptionUseCase {

    private final PaymentHandler paymentHandler;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void addSubscription(AddSubscriptionInput input) {
        String isdn = input.getIsdn();
        String item = input.getItem();
        String name = input.getName();
        BigDecimal price = input.getPrice();
        Long duration = input.getDuration();
        String provider = input.getProvider();

        Subscription subscription;
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findSubscription(isdn, item, provider);

        if (subscriptionOptional.isPresent()) {
            subscription = subscriptionOptional.get();
            if (subscription.isActive()) {
                throw new BusinessException(ErrorCode.SUBSCRIPTION_EXISTED);
            }

            this.reNewSubscription(subscription);
        } else {
            subscription = Subscription.newSubscription(name, isdn, item, provider, price, duration);
            subscriptionRepository.save(subscription);
        }
        createPayment(subscription);
    }

    @Override
    public void reNewSubscription(Subscription subscription) {
        if (subscription.isActive()) {
            throw new RuntimeException();
        }

        subscription.reNew();
        subscriptionRepository.update(subscription);
        createPayment(subscription);
    }

    @Override
    public Subscription checkSubscription(CheckSubscriptionInput input) {
        String isdn = input.getIsdn();
        String item = input.getItem();
        String provider = input.getProvider();

        return subscriptionRepository.findSubscription(isdn, item, provider)
                .orElse(null);
    }

    protected void createPayment(Subscription subscription) {
        CreatePaymentInput createPaymentInput = new CreatePaymentInput(
                Business.RENEW_SUBSCRIPTION,
                String.valueOf(subscription.getId()),
                subscription.getPrice(),
                "Renew subscription for item: " + subscription.getItem()
        );
        paymentHandler.createPayment(createPaymentInput);
    }
}
