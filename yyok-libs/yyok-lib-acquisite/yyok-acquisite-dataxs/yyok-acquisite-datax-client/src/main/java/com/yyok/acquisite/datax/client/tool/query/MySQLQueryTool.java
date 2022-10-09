package com.yyok.acquisite.datax.client.tool.query;

import com.yyok.acquisite.datax.client.entity.JobDatasource;

import java.sql.SQLException;

/**
 * mysql数据库使用的查询工具
 *
 */
public class MySQLQueryTool extends BaseQueryTool implements QueryToolInterface {

    public MySQLQueryTool(JobDatasource jobDatasource) throws SQLException {
        super(jobDatasource);
    }

}
