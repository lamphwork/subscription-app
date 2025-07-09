package com.vtt.core.infrastructure.jpa.impl;

import com.vtt.core.domain.model.Subscription;
import com.vtt.core.domain.port.repository.SubscriptionRepository;
import com.vtt.core.infrastructure.jpa.entities.SubscriptionEntity;
import com.vtt.core.infrastructure.jpa.repository.SubscriptionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaSubscriptionRepo implements SubscriptionRepository {

    private final SubscriptionEntityRepository jpaRepo;

    @Override
    public Optional<Subscription> findSubscription(String isdn, String item, String provider) {
        return jpaRepo.findByIsdnAndItemAndProvider(isdn, item, provider).map(this::toModel);
    }

    @Override
    public Subscription save(Subscription subscription) {
        SubscriptionEntity savedEntity = jpaRepo.save(toEntity(subscription));
        return toModel(savedEntity);
    }

    @Override
    public Subscription update(Subscription subscription) {
        return jpaRepo.findById(subscription.getId())
                .map(entity -> {
                    SubscriptionEntity newEntity = toEntity(subscription);
                    return jpaRepo.save(newEntity);
                })
                .map(this::toModel)
                .orElse(null);
    }

    @Override
    public List<Subscription> findRenewableToday() {
        OffsetDateTime startTime = OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS);
        OffsetDateTime endTime = OffsetDateTime.now().plusDays(1).truncatedTo(ChronoUnit.DAYS);
        return jpaRepo.findRenewableInDay(startTime, endTime).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    /**
     * map jpa entity to domain model
     *
     * @param entity jpa entity
     * @return domain model
     */
    Subscription toModel(SubscriptionEntity entity) {
        return new Subscription(
                entity.getId(),
                entity.getName(),
                entity.getIsdn(),
                entity.getItem(),
                entity.getProvider(),
                entity.getPrice(),
                entity.getDuration(),
                entity.isActive(),
                entity.getLastCharge(),
                entity.getNextCharge()
        );
    }

    /**
     * map domain model to jpa entity
     *
     * @param subscription domain model
     * @return jpa entity
     */
    SubscriptionEntity toEntity(Subscription subscription) {
        SubscriptionEntity entity = new SubscriptionEntity();
        BeanUtils.copyProperties(subscription, entity);
        return entity;
    }
}
