package com.vtt.core.domain.port.repository;


import com.vtt.core.domain.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {

    Optional<Subscription> findSubscription(String isdn, String item, String provider);

    Subscription save(Subscription subscription);

    Subscription update(Subscription subscription);

    List<Subscription> findRenewableToday();

}
