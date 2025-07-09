package com.vtt.core.presentation.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtt.common.kafka.Topics;
import com.vtt.common.kafka.message.PaymentResultMessage;
import com.vtt.core.domain.model.Payment;
import com.vtt.core.domain.port.payment.PaymentHandler;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final ObjectMapper objectMapper;
    private final PaymentHandler paymentHandler;

    @KafkaListener(topics = Topics.DEBIT_RESULT, groupId = "subscription-core")
    public void consumerSubscriptionPaymentSuccess(ConsumerRecord<String, String> record) throws JsonProcessingException {
        String message = record.value();
        PaymentResultMessage paymentResult = objectMapper.readValue(message, PaymentResultMessage.class);

        String paymentId = paymentResult.getPaymentId();
        boolean success = paymentResult.isSuccess();

        if (success) {
            paymentHandler.updatePaymentStatus(paymentId, Payment.PaymentStatus.SUCCESS);
        } else {
            paymentHandler.updatePaymentStatus(paymentId, Payment.PaymentStatus.ERROR);
        }
    }
}
