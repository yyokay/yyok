package com.yyok.share.admin.generator.service;

import com.yyok.share.admin.generator.domain.GenConfig;

/**
 * @author yyok
 * @date 2019-01-14
 */
public interface IGenConfigService {

    /**
     * 查询表配置
     *
     * @param tableName 表名
     * @return 表配置
     */
    GenConfig find(String tableName);

    /**
     * 更新表配置
     *
     * @param tableName 表名
     * @param genConfig 表配置
     * @return 表配置
     */
    GenConfig update(String tableName, GenConfig genConfig);
}
