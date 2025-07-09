package com.vtt.core.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtt.common.exception.InfraException;
import com.vtt.common.kafka.Topics;
import com.vtt.common.kafka.message.PaymentRequestMessage;
import com.vtt.core.domain.model.Payment;
import com.vtt.core.domain.port.payment.PaymentEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPublisher implements PaymentEventPublisher {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(Payment payment) {
        PaymentRequestMessage paymentRequestMessage = new PaymentRequestMessage(
                String.valueOf(payment.getId()),
                payment.getPaymentSource(),
                payment.getDescription(),
                payment.getAmount(),
                payment.getBusiness(),
                payment.getSourceId(),
                OffsetDateTime.now()
        );
        try {
            kafkaTemplate.send(Topics.DEBIT_REQUEST, objectMapper.writeValueAsString(paymentRequestMessage));
        } catch (JsonProcessingException e) {
            throw new InfraException(e);
        }
    }
}
