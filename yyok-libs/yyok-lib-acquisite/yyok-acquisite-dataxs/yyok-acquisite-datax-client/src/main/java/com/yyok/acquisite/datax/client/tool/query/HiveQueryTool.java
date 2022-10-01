package com.yyok.acquisite.datax.client.tool.query;

import com.yyok.acquisite.datax.client.entity.JobDatasource;

import java.sql.SQLException;

/**
 * hive
 */
public class HiveQueryTool extends BaseQueryTool implements QueryToolInterface {
    public HiveQueryTool(JobDatasource jobDatasource) throws SQLException {
        super(jobDatasource);
    }
}
