package com.vtt.core.presentation.rest.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddSubscriptionRequest {
    private String name;
    private String isdn;
    private String item;
    private String provider;
    private BigDecimal price;
    private Long duration;
}
