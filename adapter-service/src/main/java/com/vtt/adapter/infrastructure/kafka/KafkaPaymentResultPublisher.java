package com.vtt.adapter.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtt.adapter.domain.port.payment.PaymentResultPublisher;
import com.vtt.common.exception.InfraException;
import com.vtt.common.kafka.Topics;
import com.vtt.common.kafka.message.PaymentResultMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPaymentResultPublisher implements PaymentResultPublisher {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(PaymentResultMessage paymentResultMessage) {
        try {
            String message = objectMapper.writeValueAsString(paymentResultMessage);
            kafkaTemplate.send(Topics.DEBIT_RESULT, message);
        } catch (JsonProcessingException e) {
            throw new InfraException(e);
        }
    }
}
