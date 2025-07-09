package com.vtt.core.presentation.job;

import com.vtt.core.domain.model.Subscription;
import com.vtt.core.domain.port.repository.SubscriptionRepository;
import com.vtt.core.domain.port.usecase.SubscriptionUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReNewSubscriptionJob {

    private final SubscriptionUseCase subscriptionUseCase;
    private final SubscriptionRepository subscriptionRepository;

    @Scheduled(cron = "0 0 */2 * * *")
    @Scheduled(fixedDelay = 5000)
    public void reNewSubscription() {
        List<Subscription> subscriptions = subscriptionRepository.findRenewableToday();
        log.info("Found {} subscriptions to renew", subscriptions.size());
        for (Subscription subscription : subscriptions) {
            if (!subscription.isActive()) {
                continue;
            }
            subscriptionUseCase.reNewSubscription(subscription);
        }
    }
}
