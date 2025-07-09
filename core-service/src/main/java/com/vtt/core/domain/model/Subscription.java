package com.vtt.core.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;

@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Subscription {

    private Long id;
    private String name;
    private String isdn;
    private String item;
    private String provider;
    private BigDecimal price;
    private Long duration;
    private boolean active;
    private OffsetDateTime lastCharge;
    private OffsetDateTime nextCharge;

    public static Subscription newSubscription(String name, String isdn, String item, String provider, BigDecimal price, Long duration) {
        OffsetDateTime currentTime = OffsetDateTime.now();
        OffsetDateTime nextChargeTime = currentTime.plusDays(duration);
        return new Subscription(
                null,
                name,
                isdn,
                item,
                provider,
                price,
                duration,
                true,
                currentTime,
                nextChargeTime
        );
    }

    public void reNew() {
        OffsetDateTime currentTime = OffsetDateTime.now();
        setActive(true);
        setLastCharge(currentTime);
        setNextCharge(currentTime.plusDays(duration));
    }

    void disable() {
        setActive(false);
    }
}
