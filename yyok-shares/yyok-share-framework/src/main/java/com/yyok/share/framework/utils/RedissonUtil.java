package com.yyok.share.framework.utils;

/*
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

//@Component
public class RedissonUtil {

    //@Autowired
    private RedissonClient redissonClient;

    */
/**
 * 锁住不设置超时时间(拿不到lock就不罢休，不然线程就一直block)
 *
 * @param lockKey
 * @return org.redisson.api.RLock
 * <p>
 * leaseTime为加锁时间，单位为秒
 * @param lockKey
 * @param leaseTime
 * @return org.redisson.api.RLock
 * @param lockKey
 * @param unit
 * @param timeout
 * @return org.redisson.api.RLock
 * <p>
 * 尝试获取锁
 * @param lockKey
 * @param unit
 * @param waitTime
 * @param leaseTime
 * @return boolean
 * <p>
 * 通过lockKey解锁
 * @param lockKey
 * @return void
 * <p>
 * 直接通过锁解锁
 * @param lock
 * @return void
 *//*

    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    */
/**
 * leaseTime为加锁时间，单位为秒
 * @param lockKey
 * @param leaseTime
 * @return org.redisson.api.RLock
 *//*

    public RLock lock(String lockKey, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return null;
    }

    */
/**
 * @param lockKey
 * @param unit
 * @param timeout
 * @return org.redisson.api.RLock
 *//*

    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    */
/**
 * 尝试获取锁
 * @param lockKey
 * @param unit
 * @param waitTime
 * @param leaseTime
 * @return boolean
 *//*

    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    */
/**
 * 通过lockKey解锁
 * @param lockKey
 * @return void
 *//*

    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    */
/**
 * 直接通过锁解锁
 * @param lock
 * @return void
 *//*

    public void unlock(RLock lock) {
        lock.unlock();
    }
}*/
