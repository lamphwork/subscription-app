package com.vtt.core.domain.port.usecase.params;

import lombok.Data;

@Data
public class CheckSubscriptionInput {
    private String provider;
    private String isdn;
    private String item;
}
