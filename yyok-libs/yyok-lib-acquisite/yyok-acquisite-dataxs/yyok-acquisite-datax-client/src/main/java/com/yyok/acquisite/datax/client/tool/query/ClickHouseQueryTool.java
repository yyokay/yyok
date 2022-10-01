package com.yyok.acquisite.datax.client.tool.query;

import com.yyok.acquisite.datax.client.entity.JobDatasource;

import java.sql.SQLException;

/**
 * ClickHouse
 */
public class ClickHouseQueryTool extends BaseQueryTool implements QueryToolInterface {
    /**
     * 构造方法
     *
     * @param jobJdbcDatasource
     */
  public ClickHouseQueryTool(JobDatasource jobJdbcDatasource) throws SQLException {
        super(jobJdbcDatasource);
    }
}
