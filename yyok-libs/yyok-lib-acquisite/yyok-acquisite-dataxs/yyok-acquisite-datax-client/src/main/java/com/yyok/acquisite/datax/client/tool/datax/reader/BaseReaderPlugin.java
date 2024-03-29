package com.yyok.acquisite.datax.client.tool.datax.reader;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.yyok.acquisite.datax.client.entity.JobDatasource;
import com.yyok.acquisite.datax.client.tool.datax.BaseDataxPlugin;
import com.yyok.acquisite.datax.client.tool.pojo.DataxHbasePojo;
import com.yyok.acquisite.datax.client.tool.pojo.DataxHivePojo;
import com.yyok.acquisite.datax.client.tool.pojo.DataxMongoDBPojo;
import com.yyok.acquisite.datax.client.tool.pojo.DataxRdbmsPojo;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Reader
 *
 */
public abstract class BaseReaderPlugin extends BaseDataxPlugin {


    @Override
    public Map<String, Object> build(DataxRdbmsPojo plugin) {
        //构建
        Map<String, Object> readerObj = Maps.newLinkedHashMap();
        readerObj.put("name", getName());
        Map<String, Object> parameterObj = Maps.newLinkedHashMap();
        Map<String, Object> connectionObj = Maps.newLinkedHashMap();

        JobDatasource jobDatasource = plugin.getJobDatasource();
        parameterObj.put("username", jobDatasource.getJdbcUsername());
        parameterObj.put("password", jobDatasource.getJdbcPassword());

        //判断是否是 querySql
        if (StrUtil.isNotBlank(plugin.getQuerySql())) {
            connectionObj.put("querySql", ImmutableList.of(plugin.getQuerySql()));
        } else {
            parameterObj.put("column", plugin.getRdbmsColumns());
            //判断是否有where
            if (StringUtils.isNotBlank(plugin.getWhereParam())) {
                parameterObj.put("where", plugin.getWhereParam());
            }
            connectionObj.put("table", plugin.getTables());
        }
        parameterObj.put("splitPk",plugin.getSplitPk());
        connectionObj.put("jdbcUrl", ImmutableList.of(jobDatasource.getJdbcUrl()));

        parameterObj.put("connection", ImmutableList.of(connectionObj));

        readerObj.put("parameter", parameterObj);

        return readerObj;
    }

    @Override
    public Map<String, Object> buildHive(DataxHivePojo dataxHivePojo) {
        return null;
    }

    @Override
    public Map<String, Object> buildHbase(DataxHbasePojo dataxHbasePojo) { return null; }

    @Override
    public Map<String, Object> buildMongoDB(DataxMongoDBPojo dataxMongoDBPojo) {
        return null;
    }
}
