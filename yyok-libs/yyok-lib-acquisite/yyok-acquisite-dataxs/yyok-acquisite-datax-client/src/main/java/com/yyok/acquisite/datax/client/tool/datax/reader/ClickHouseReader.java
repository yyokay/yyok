package com.yyok.acquisite.datax.client.tool.datax.reader;

import java.util.Map;

public class ClickHouseReader  extends BaseReaderPlugin implements DataxReaderInterface {
    @Override
    public String getName() {
        return "clickhousereader";
    }


    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
