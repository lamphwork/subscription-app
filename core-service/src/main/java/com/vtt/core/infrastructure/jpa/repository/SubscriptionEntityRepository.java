package com.vtt.core.infrastructure.jpa.repository;

import com.vtt.core.infrastructure.jpa.entities.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface SubscriptionEntityRepository extends JpaRepository<SubscriptionEntity, Long> {

    Optional<SubscriptionEntity> findByIsdnAndItemAndProvider(String isdn, String item, String provider);

    @Query("select s from SubscriptionEntity s where s.nextCharge >= :startTime and s.nextCharge < :endTime")
    List<SubscriptionEntity> findRenewableInDay(OffsetDateTime startTime, OffsetDateTime endTime);
}
