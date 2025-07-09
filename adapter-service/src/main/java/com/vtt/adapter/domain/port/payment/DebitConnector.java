package com.vtt.adapter.domain.port.payment;

import com.vtt.adapter.domain.port.payment.params.DebitInput;
import com.vtt.adapter.domain.port.payment.params.DebitResult;

public interface DebitConnector {

    String getCode();
    DebitResult debit(DebitInput debitInput);
}
