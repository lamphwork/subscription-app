package com.vtt.common.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
public class RedisLocker {

    private final RedissonClient redissonClient;

    public <T> T executeWithLock(String lockName, Integer waitTime, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(lockName);
        boolean isLock = false;
        try {
            isLock = lock.tryLock(waitTime, waitTime, TimeUnit.SECONDS);
            log.info("try lock {}", lockName);
            if (isLock) {
                return supplier.get();
            } else {
                throw new RuntimeException("Can not aquire lock " + lockName);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            if (isLock && lock.isHeldByCurrentThread()) {
                log.info("release lock {}", lockName);
                lock.unlock();
            }
        }
    }

    public void executeWithLock(String lockName, Integer waitTime, Runnable runnable) {
        executeWithLock(lockName, waitTime, () -> {
            runnable.run();
            return null;
        });
    }
}
