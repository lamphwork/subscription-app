package com.vtt.adapter.presentation.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtt.adapter.domain.port.payment.DebitHandler;
import com.vtt.adapter.domain.port.payment.params.DebitInput;
import com.vtt.common.kafka.Topics;
import com.vtt.common.kafka.message.PaymentRequestMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestPaymentConsumer {

    private final DebitHandler debitHandler;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = Topics.DEBIT_REQUEST, groupId = "payment-adapter")
    public void handlePaymentRequest(ConsumerRecord<String, String> record) throws JsonProcessingException {
        String message = record.value();
        log.info("Received payment request : {}", message);

        PaymentRequestMessage paymentRequest = objectMapper.readValue(message, PaymentRequestMessage.class);
        log.info("Payment request : {}", paymentRequest);
        DebitInput debitInput = new DebitInput();
        // TODO fill data debitInput
        debitHandler.handle(debitInput);
    }
}
