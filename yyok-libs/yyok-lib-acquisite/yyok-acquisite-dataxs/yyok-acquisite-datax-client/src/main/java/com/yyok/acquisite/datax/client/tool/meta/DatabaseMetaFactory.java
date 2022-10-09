package com.yyok.acquisite.datax.client.tool.meta;

import com.yyok.acquisite.datax.client.util.JdbcConstants;

/**
 * meta信息工厂
 *
 */
public class DatabaseMetaFactory {

    //根据数据库类型返回对应的接口
    public static DatabaseInterface getByDbType(String dbType) {
        if (JdbcConstants.MYSQL.equals(dbType)) {
            return MySQLDatabaseMeta.getInstance();
        } else if (JdbcConstants.ORACLE.equals(dbType)) {
            return OracleDatabaseMeta.getInstance();
        } else if (JdbcConstants.POSTGRESQL.equals(dbType)) {
            return PostgresqlDatabaseMeta.getInstance();
        } else if (JdbcConstants.SQL_SERVER.equals(dbType)) {
            return SqlServerDatabaseMeta.getInstance();
        } else if (JdbcConstants.HIVE.equals(dbType)) {
            return HiveDatabaseMeta.getInstance();
        }else if(JdbcConstants.CLICKHOUSE.equals(dbType)) {
            return ClickHouseDataBaseMeta.getInstance();
        } else if(JdbcConstants.HBASE20XSQL.equals(dbType)) {
            return Hbase20xsqlMeta.getInstance();
        } else {
            throw new UnsupportedOperationException("暂不支持的类型：".concat(dbType));
        }
    }
}
