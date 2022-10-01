package com.yyok.acquisite.datax.client.tool.datax.writer;


import java.util.Map;

/**
 * sql server writer构建类
 */
public class SqlServerlWriter extends BaseWriterPlugin implements DataxWriterInterface {
    @Override
    public String getName() {
        return "sqlserverwriter";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
