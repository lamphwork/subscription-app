package com.vtt.core.presentation.job;

import com.vtt.common.redis.LockAction;
import com.vtt.common.redis.RedisLocker;
import com.vtt.core.domain.model.CustPayment;
import com.vtt.core.domain.port.payment.PaymentHandler;
import com.vtt.core.domain.port.payment.input.CreatePaymentInput;
import com.vtt.core.domain.port.repository.CustPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutoRenewJob {

    private final RedisLocker redisLocker;
    private final PaymentHandler paymentHandler;
    private final CustPaymentRepository custPaymentRepository;

    //    @Scheduled(cron = "0 0 */2 * * *")
    @Scheduled(fixedDelay = 5000)
    public void reNewSubscription() {
        int waitTimeSeconds = 5;
        redisLocker.executeWithLock("renew-subscription-job", waitTimeSeconds, () -> {
            List<CustPayment> custPayments = custPaymentRepository.findRenewableToday();
            log.info("Found {} subscriptions to renew", custPayments.size());
            for (CustPayment custPayment : custPayments) {
                if (!custPayment.isAutoRenew()) {
                    continue;
                }

                CreatePaymentInput createPaymentInput = new CreatePaymentInput();
                // TODO fill detail

                paymentHandler.createPayment(createPaymentInput);
            }
        });
    }

}
