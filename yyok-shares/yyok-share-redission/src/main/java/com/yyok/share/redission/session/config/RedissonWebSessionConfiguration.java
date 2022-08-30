package com.yyok.share.redission.session.config;

import com.yyok.share.redission.session.ReactiveRedissonSessionRepository;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.session.config.annotation.web.server.SpringWebSessionConfiguration;

import java.util.Map;

/**
 * Deprecated. Use spring-session implementation based on Redisson Redis Data module
 *
 * @author Nikita Koksharov
 */
@Configuration
public class RedissonWebSessionConfiguration extends SpringWebSessionConfiguration implements ImportAware {

    private Integer maxInactiveIntervalInSeconds;
    private String keyPrefix;

    @Bean
    public ReactiveRedissonSessionRepository sessionRepository(
            RedissonClient redissonClient, ApplicationEventPublisher eventPublisher) {
        ReactiveRedissonSessionRepository repository = new ReactiveRedissonSessionRepository(redissonClient, eventPublisher, keyPrefix);
        repository.setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
        return repository;
    }

    public void setMaxInactiveIntervalInSeconds(Integer maxInactiveIntervalInSeconds) {
        this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map<String, Object> map = importMetadata.getAnnotationAttributes(EnableRedissonWebSession.class.getName());
        AnnotationAttributes attrs = AnnotationAttributes.fromMap(map);
        keyPrefix = attrs.getString("keyPrefix");
        maxInactiveIntervalInSeconds = attrs.getNumber("maxInactiveIntervalInSeconds");
    }

}
