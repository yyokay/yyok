package com.yyok.acquisite.datax.client.tool.datax.reader;

import java.util.Map;

/**
 * oracle reader 构建类
 */
public class OracleReader extends BaseReaderPlugin implements DataxReaderInterface {
    @Override
    public String getName() {
        return "oraclereader";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
