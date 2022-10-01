package com.yyok.acquisite.datax.client.tool.datax.writer;

import java.util.Map;

/**
 * mysql writer构建类
 *
 */
public class MysqlWriter extends BaseWriterPlugin implements DataxWriterInterface {
    @Override
    public String getName() {
        return "mysqlwriter";
    }


    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
