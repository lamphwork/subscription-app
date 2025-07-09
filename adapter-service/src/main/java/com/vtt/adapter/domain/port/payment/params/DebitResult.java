package com.vtt.adapter.domain.port.payment.params;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebitResult {
    private String refId;
    private boolean success;
}
