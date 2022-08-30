package com.yyok.share.redission.session;

import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.session.ReactiveSessionRepository;
import reactor.core.publisher.Mono;

/**
 * Deprecated. Use spring-session implementation based on Redisson Redis Data module
 *
 * @author Nikita Koksharov
 */
public class ReactiveRedissonSessionRepository implements ReactiveSessionRepository<RedissonSessionRepository.RedissonSession> {

    private final RedissonSessionRepository repository;

    public ReactiveRedissonSessionRepository(RedissonClient redissonClient, ApplicationEventPublisher eventPublisher,
                                             String keyPrefix) {
        this.repository = new RedissonSessionRepository(redissonClient, eventPublisher, keyPrefix);
    }

    public void setDefaultMaxInactiveInterval(int defaultMaxInactiveInterval) {
        repository.setDefaultMaxInactiveInterval(defaultMaxInactiveInterval);
    }

    @Override
    public Mono<RedissonSessionRepository.RedissonSession> createSession() {
        return Mono.fromCallable(() -> {
            return repository.createSession();
        });
    }

    @Override
    public Mono<Void> save(RedissonSessionRepository.RedissonSession session) {
        // session changes are stored in real-time
        return Mono.empty();
    }

    @Override
    public Mono<RedissonSessionRepository.RedissonSession> findById(String id) {
        return Mono.fromCallable(() -> {
            return repository.findById(id);
        });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return Mono.fromRunnable(() -> {
            repository.deleteById(id);
        });
    }

}
