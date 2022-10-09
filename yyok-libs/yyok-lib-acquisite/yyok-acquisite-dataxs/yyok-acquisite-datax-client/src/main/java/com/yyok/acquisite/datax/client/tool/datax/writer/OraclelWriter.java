package com.yyok.acquisite.datax.client.tool.datax.writer;

import java.util.Map;

/**
 * oracle writer构建类
 */
public class OraclelWriter extends BaseWriterPlugin implements DataxWriterInterface {
    @Override
    public String getName() {
        return "oraclewriter";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
