package com.yyok.acquisite.datax.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.yyok.acquisite.datax.client.dto.DataXJsonBuildDto;
import com.yyok.acquisite.datax.client.entity.JobDatasource;
import com.yyok.acquisite.datax.client.service.IDataxJsonService;
import com.yyok.acquisite.datax.client.service.IJobDatasourceService;
import com.yyok.acquisite.datax.client.tool.datax.DataxJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * com.yyok.acquisite.datax json构建实现类
 *
 */
@Service
public class IDataxJsonServiceImpl implements IDataxJsonService {

    @Autowired
    private IJobDatasourceService jobJdbcDatasourceService;

    @Override
    public String buildJobJson(DataXJsonBuildDto dataXJsonBuildDto) {
        DataxJsonHelper dataxJsonHelper = new DataxJsonHelper();
        // reader
        JobDatasource readerDatasource = jobJdbcDatasourceService.getById(dataXJsonBuildDto.getReaderDatasourceId());
        // reader plugin init
        dataxJsonHelper.initReader(dataXJsonBuildDto, readerDatasource);
        JobDatasource writerDatasource = jobJdbcDatasourceService.getById(dataXJsonBuildDto.getWriterDatasourceId());
        dataxJsonHelper.initWriter(dataXJsonBuildDto, writerDatasource);

        return JSON.toJSONString(dataxJsonHelper.buildJob());
    }
}