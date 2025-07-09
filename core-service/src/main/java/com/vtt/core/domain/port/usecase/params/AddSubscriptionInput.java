package com.vtt.core.domain.port.usecase.params;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddSubscriptionInput {

    private String name;
    private String isdn;
    private String item;
    private String provider;
    private BigDecimal price;
    private Long duration;
}
