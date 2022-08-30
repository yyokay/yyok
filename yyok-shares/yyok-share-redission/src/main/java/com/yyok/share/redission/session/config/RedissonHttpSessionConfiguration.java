package com.yyok.share.redission.session.config;

import com.yyok.share.redission.session.RedissonSessionRepository;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;

import java.util.Map;

/**
 * Deprecated. Use spring-session implementation based on Redisson Redis Data module
 *
 * @author Nikita Koksharov
 */
@Configuration
public class RedissonHttpSessionConfiguration extends SpringHttpSessionConfiguration implements ImportAware {

    private Integer maxInactiveIntervalInSeconds;
    private String keyPrefix;

    @Bean
    public RedissonSessionRepository sessionRepository(
            RedissonClient redissonClient, ApplicationEventPublisher eventPublisher) {
        RedissonSessionRepository repository = new RedissonSessionRepository(redissonClient, eventPublisher, keyPrefix);
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
        Map<String, Object> map = importMetadata.getAnnotationAttributes(EnableRedissonHttpSession.class.getName());
        AnnotationAttributes attrs = AnnotationAttributes.fromMap(map);
        keyPrefix = attrs.getString("keyPrefix");
        maxInactiveIntervalInSeconds = attrs.getNumber("maxInactiveIntervalInSeconds");
    }

}
