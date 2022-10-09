package com.yyok.acquisite.datax.client.tool.datax.reader;

import java.util.Map;

/**
 * postgresql 构建类
 *
 */
public class PostgresqlReader extends BaseReaderPlugin implements DataxReaderInterface {
    @Override
    public String getName() {
        return "postgresqlreader";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
