package com.vtt.core.infrastructure.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "subscription")
@SequenceGenerator(name = "subscription_seq", sequenceName = "subscription_seq", allocationSize = 1)
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "isdn")
    private String isdn;

    @Column(name = "item")
    private String item;

    @Column(name = "provider")
    private String provider;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "active")
    private boolean active;

    @Column(name = "last_charge")
    private OffsetDateTime lastCharge;

    @Column(name = "next_charge")
    private OffsetDateTime nextCharge;
}
