package com.yyok.acquisite.datax.client.tool.pojo;

import com.yyok.acquisite.datax.client.dto.UpsertInfo;
import com.yyok.acquisite.datax.client.entity.JobDatasource;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 用于传参，构建json
 *
 */
@Data
public class DataxMongoDBPojo {

    /**
     * hive列名
     */
    private List<Map<String, Object>> columns;

    /**
     * 数据源信息
     */
    private JobDatasource jdbcDatasource;

    private String address;

    private String dbName;

    private String readerTable;

    private String writerTable;

    private UpsertInfo upsertInfo;

}