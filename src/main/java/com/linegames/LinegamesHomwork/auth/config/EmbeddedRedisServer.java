package com.linegames.LinegamesHomwork.auth.config;

import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 내부용 REDIS 서버
 */
@Configuration // 외부용 REDIS  서버를 사용하는 경우 주석처리
public class EmbeddedRedisServer {
    private RedisServer redisServer;

    public EmbeddedRedisServer(RedisProperties redisProperties) {
        this.redisServer = new RedisServer(redisProperties.getRedisPort());
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}
