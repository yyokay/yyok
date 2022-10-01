package com.yyok.acquisite.datax.client.tool.datax;

import java.util.Map;

/**
 * 构建 com.yyok.acquisite.datax json的基础接口
 *
 */
public interface DataxJsonInterface {

    Map<String, Object> buildJob();

    Map<String, Object> buildSetting();

    Map<String, Object> buildContent();

    Map<String, Object> buildReader();

    Map<String, Object> buildHiveReader();

    Map<String, Object> buildHiveWriter();

    Map<String, Object> buildHBaseReader();

    Map<String, Object> buildHBaseWriter();

    Map<String, Object> buildMongoDBReader();

    Map<String, Object> buildMongoDBWriter();

    Map<String, Object> buildWriter();
}
