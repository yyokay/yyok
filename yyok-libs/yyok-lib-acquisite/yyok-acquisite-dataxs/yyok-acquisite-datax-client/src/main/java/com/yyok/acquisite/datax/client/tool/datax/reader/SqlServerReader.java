package com.yyok.acquisite.datax.client.tool.datax.reader;

import java.util.Map;

/**
 * sqlserver reader 构建类
 *
 */
public class SqlServerReader extends BaseReaderPlugin implements DataxReaderInterface {
    @Override
    public String getName() {
        return "sqlserverreader";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
