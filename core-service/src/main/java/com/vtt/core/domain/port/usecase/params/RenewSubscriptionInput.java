package com.vtt.core.domain.port.usecase.params;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RenewSubscriptionInput {
    
    private Long subscriptionId;
}
