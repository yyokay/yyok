package com.yyok.share.framework.config;
/*
import com.yyok.share.framework.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@ComponentScan
@EnableCaching
@Log4j2
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minimumIdleSize;

    @Value("${spring.redis.edis.pool.max-idle}")
    private int connectionPoolSize;

    @Bean(destroyMethod = "shutdown" ,name = "redissonClient")
    RedissonClient redisson() throws IOException {
        Config config = new Config();
        log.info(host);
        log.info(port);
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setKeepAlive(true)
                .setConnectionMinimumIdleSize(minimumIdleSize)
                .setDnsMonitoringInterval(30000L)
                .setConnectionPoolSize(connectionPoolSize);

        if (StringUtils.isNotBlank(password)) {
            serverConfig.setPassword(password);
        }

        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(@Qualifier("redissonClient") RedissonClient redissonClient) {
        Map config = new HashMap();
        return new RedissonSpringCacheManager(redissonClient, config);
    }
}*/
