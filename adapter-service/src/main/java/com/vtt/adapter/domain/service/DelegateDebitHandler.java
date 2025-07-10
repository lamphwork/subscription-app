package com.vtt.adapter.domain.service;

import com.vtt.adapter.domain.exception.ErrorCode;
import com.vtt.adapter.domain.port.payment.DebitConnector;
import com.vtt.adapter.domain.port.payment.DebitHandler;
import com.vtt.adapter.domain.port.payment.PaymentResultPublisher;
import com.vtt.adapter.domain.port.payment.params.DebitInput;
import com.vtt.adapter.domain.port.payment.params.DebitResult;
import com.vtt.common.exception.BusinessException;
import com.vtt.common.kafka.message.PaymentResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DelegateDebitHandler implements DebitHandler {

    private final Map<String, DebitConnector> debitConnectorsMap;
    private final PaymentResultPublisher publisher;

    public DelegateDebitHandler(final List<DebitConnector> debitConnectors, final PaymentResultPublisher publisher) {
        this.debitConnectorsMap = debitConnectors.stream().collect(Collectors.toMap(
                DebitConnector::getCode,
                Function.identity()
        ));
        this.publisher = publisher;
    }

    @Override
    public void handle(DebitInput input) {
        DebitConnector connector = debitConnectorsMap.get(input.getPartnerCode());
        if (connector == null) {
            throw new BusinessException(ErrorCode.PARTNER_NOT_FOUND);
        }

        DebitResult result = connector.debit(input);
        log.info("Debit result: {}", result);
        PaymentResultMessage paymentResultMessage = new PaymentResultMessage();
        // TODO fill paymentResultMessage
        publisher.publish(paymentResultMessage);
    }
}
