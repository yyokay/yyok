package com.yyok.acquisite.datax.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyok.acquisite.datax.client.entity.JobDatasource;
import com.yyok.acquisite.datax.client.entity.JobDatasource;
import org.apache.ibatis.annotations.Mapper;

/**
 * jdbc数据源配置表数据库访问层
 *
 */
@Mapper
public interface JobDatasourceMapper extends BaseMapper<JobDatasource> {
    int update(JobDatasource datasource);

}