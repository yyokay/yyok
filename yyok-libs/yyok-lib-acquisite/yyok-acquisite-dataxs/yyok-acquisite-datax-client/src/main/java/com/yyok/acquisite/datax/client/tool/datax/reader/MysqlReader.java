package com.yyok.acquisite.datax.client.tool.datax.reader;


import java.util.Map;

/**
 * mysql reader 构建类
 *
 */
public class MysqlReader extends BaseReaderPlugin implements DataxReaderInterface {
    @Override
    public String getName() {
        return "mysqlreader";
    }


    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
