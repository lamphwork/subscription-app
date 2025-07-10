package com.vtt.common.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Configuration
@ConditionalOnBean(RedissonClient.class)
@RequiredArgsConstructor
public class LogActionAspect {

    private final RedissonClient redissonClient;

    @Around("@annotation(lockAction)")
    Object lockAction(ProceedingJoinPoint joinPoint, LockAction lockAction) {
        RLock lock = redissonClient.getLock(lockAction.value());
        boolean isLock = false;
        try {
            isLock = lock.tryLock(5, 10, TimeUnit.SECONDS);
            log.info("try lock {}", lockAction.value());
            if (isLock) {
                return joinPoint.proceed();
            } else {
                throw new RuntimeException("Can not aquire lock " + lockAction.value());
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            if (isLock && lock.isHeldByCurrentThread()) {
                log.info("release lock {}", lockAction.value());
                lock.unlock();
            }
        }
    }
}
