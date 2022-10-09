package com.yyok.acquisite.datax.client.tool.query;

import com.yyok.acquisite.datax.client.entity.JobDatasource;

import java.sql.SQLException;

public class PostgresqlQueryTool extends BaseQueryTool implements QueryToolInterface {
    public PostgresqlQueryTool(JobDatasource jobDatasource) throws SQLException {
        super(jobDatasource);
    }

}
