package com.yyok.share.redission.configuration;

import com.yyok.share.redission.mq.RedissonMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQ配置
 */
@Configuration
public class MQConfiguration {

    @Bean
    @ConditionalOnMissingBean(RedissonMQListener.class)
    public RedissonMQListener RedissonMQListener() {
        return new RedissonMQListener();
    }
}
