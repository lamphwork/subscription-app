package com.vtt.common.redis;

import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisLocker locker(RedissonClient client) {
        return new RedisLocker(client);
    }
}
