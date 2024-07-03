package com.officerschool.courselottery.starter.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 6/24/24
 */
@Configuration
public class CacheConfig {
    @Bean
    public Cache<String, String> caffeineCache() {
        return Caffeine.newBuilder()
                .maximumSize(5000) // 缓存的最大条数
                .expireAfterWrite(5, TimeUnit.MINUTES) // 最后一次写入后，缓存的过期时间
                .build();
    }
}
