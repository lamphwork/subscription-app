package com.vtt.adapter.infrastructure.http;

import com.vtt.adapter.domain.port.payment.DebitConnector;
import com.vtt.adapter.domain.port.payment.params.DebitInput;
import com.vtt.adapter.domain.port.payment.params.DebitResult;
import com.vtt.common.http.BaseHttpClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FakeAgriBankDebitConnector extends BaseHttpClient implements DebitConnector {

    @Override
    public String getCode() {
        return "AGRIBANK";
    }

    @Override
    public DebitResult debit(DebitInput debitInput) {
        // TODO implement
        ResponseEntity<String> debitResponse = postJSON("debitUrl", null, null);
        return new DebitResult("111", true);
    }
}
