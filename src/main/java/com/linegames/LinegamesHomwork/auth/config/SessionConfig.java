package com.linegames.LinegamesHomwork.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관련 설정
 */
//@EnableSpringHttpSession // inmemory 방식
@EnableRedisHttpSession // redis 방식 - application.yml 참조
@EnableRedisRepositories
@Configuration
public class SessionConfig {

    /**
     * Inmemory 방식
     */
/*    @Bean
    public MapSessionRepository sessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }*/

    @Bean
    public RedisConnectionFactory connectionFactory(RedisProperties redisProperties) {
        return new LettuceConnectionFactory(
                redisProperties.getRedisHost(),
                redisProperties.getRedisPort());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory ) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
