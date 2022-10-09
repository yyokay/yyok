package com.yyok.acquisite.datax.client.tool.datax.reader;

import com.google.common.collect.Maps;
import com.yyok.acquisite.datax.client.tool.pojo.DataxHivePojo;

import java.util.Map;

/**
 * hive reader 构建类
 *
 */
public class HiveReader extends BaseReaderPlugin implements DataxReaderInterface {
    @Override
    public String getName() {
        return "hdfsreader";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }


    @Override
    public Map<String, Object> buildHive(DataxHivePojo plugin) {
        //构建
        Map<String, Object> readerObj = Maps.newLinkedHashMap();
        readerObj.put("name", getName());
        Map<String, Object> parameterObj = Maps.newLinkedHashMap();
        parameterObj.put("path", plugin.getReaderPath());
        parameterObj.put("defaultFS", plugin.getReaderDefaultFS());
        parameterObj.put("fileType", plugin.getReaderFileType());
        parameterObj.put("fieldDelimiter", plugin.getReaderFieldDelimiter());
        parameterObj.put("skipHeader", plugin.getSkipHeader());
        parameterObj.put("column", plugin.getColumns());
        readerObj.put("parameter", parameterObj);
        return readerObj;
    }
}
