package com.vtt.adapter.domain.port.payment;

import com.vtt.adapter.domain.port.payment.params.DebitInput;

public interface DebitHandler {

    void handle(DebitInput debitInput);
}
