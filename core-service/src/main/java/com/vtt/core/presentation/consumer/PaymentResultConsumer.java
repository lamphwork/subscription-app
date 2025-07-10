package com.vtt.core.presentation.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtt.common.exception.InfraException;
import com.vtt.common.kafka.Topics;
import com.vtt.common.kafka.message.PaymentResultMessage;
import com.vtt.core.domain.model.PaymentRequest;
import com.vtt.core.domain.port.payment.PaymentHandler;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentResultConsumer {

    private final ObjectMapper objectMapper;
    private final PaymentHandler paymentHandler;

    @KafkaListener(topics = Topics.DEBIT_RESULT, groupId = "core-service", containerFactory = "listenerContainerFactory")
    public void handlePaymentResult(ConsumerRecord<String, String> record) {
        String message = record.value();
        try {
            PaymentResultMessage paymentResult = objectMapper.readValue(message, PaymentResultMessage.class);
            paymentHandler.updatePaymentStatus(
                    paymentResult.getPaymentId(),
                    paymentResult.isSuccess() ? PaymentRequest.Status.SUCCESS : PaymentRequest.Status.ERROR
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
