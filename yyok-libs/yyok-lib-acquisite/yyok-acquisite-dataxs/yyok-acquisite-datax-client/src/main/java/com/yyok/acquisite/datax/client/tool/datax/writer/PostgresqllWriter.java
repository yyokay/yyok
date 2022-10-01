package com.yyok.acquisite.datax.client.tool.datax.writer;

import java.util.Map;

/**
 * postgresql writer构建类
 *
 */
public class PostgresqllWriter extends BaseWriterPlugin implements DataxWriterInterface {
    @Override
    public String getName() {
        return "postgresqlwriter";
    }


    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
