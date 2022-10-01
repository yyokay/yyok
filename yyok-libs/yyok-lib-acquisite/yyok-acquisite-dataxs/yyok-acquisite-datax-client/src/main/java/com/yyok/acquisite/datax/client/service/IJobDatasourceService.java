package com.yyok.acquisite.datax.client.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyok.acquisite.datax.client.entity.JobDatasource;

import java.io.IOException;
import java.util.List;

/**
 * jdbc数据源配置表服务接口
 *
 */
public interface IJobDatasourceService extends IService<JobDatasource> {
    /**
     * 测试数据源
     * @param jdbcDatasource
     * @return
     */
    Boolean dataSourceTest(JobDatasource jdbcDatasource) throws IOException;

    /**
     *更新数据源信息
     * @param datasource
     * @return
     */
    int update(JobDatasource datasource);

    /**
     * 获取所有数据源
     * @return
     */
    List<JobDatasource> selectAllDatasource();
}