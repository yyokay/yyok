package com.yyok.share.admin.monitor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 可自行扩展
 *
 * @author yyok
 * @date 2022-12-10
 */
public interface IRedisService {

    /**
     * findById
     *
     * @param key
     * @return
     */
    Page findByKey(String key, Pageable pageable);

    /**
     * 查询验证码的值
     *
     * @param key
     * @return
     */
    String getCodeVal(String key);

    /**
     * 保存验证码
     *
     * @param key
     * @param val
     */
    void saveCode(String key, Object val);

    /**
     * delete
     *
     * @param key
     */
    void delete(String key);

    /**
     * 清空所有缓存
     */
    void flushdb();
}
