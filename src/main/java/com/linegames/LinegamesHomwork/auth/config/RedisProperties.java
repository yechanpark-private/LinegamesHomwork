package com.linegames.LinegamesHomwork.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 레디스 관련 설정 로드
 */
@Configuration
public class RedisProperties {
    private int redisPort;
    private String redisHost;

    public RedisProperties(
            @Value("${spring.redis.port}") int redisPort,
            @Value("${spring.redis.host}") String redisHost) {
        this.redisPort = redisPort;
        this.redisHost = redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public String getRedisHost() {
        return redisHost;
    }
}
