package com.yyok.acquisite.datax.client.tool.query;

import com.yyok.acquisite.datax.client.entity.JobDatasource;

import java.sql.SQLException;

public class SqlServerQueryTool extends BaseQueryTool implements QueryToolInterface {
    public SqlServerQueryTool(JobDatasource jobDatasource) throws SQLException {
        super(jobDatasource);
    }
}
