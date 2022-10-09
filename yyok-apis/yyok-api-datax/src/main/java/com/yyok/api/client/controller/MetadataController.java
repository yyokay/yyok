package com.yyok.api.client.controller;

import com.yyok.acquisite.datax.client.service.IDatasourceQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 查询数据库表名，字段的控制器
 *
 */
@RestController
@RequestMapping("api/metadata")
@Api(tags = "jdbc数据库查询控制器")
public class MetadataController extends BaseController {

    @Autowired
    private IDatasourceQueryService IDatasourceQueryService;

    /**
     * 根据数据源id获取mongo库名
     *
     * @param datasourceId
     * @return
     */
    @GetMapping("/getDBs")
    @ApiOperation("根据数据源id获取mongo库名")
    public List<String> getDBs(Long datasourceId) throws IOException {
        return IDatasourceQueryService.getDBs(datasourceId);
    }


    /**
     * 根据数据源id,dbname获取CollectionNames
     *
     * @param datasourceId
     * @return
     */
    @GetMapping("/collectionNames")
    @ApiOperation("根据数据源id,dbname获取CollectionNames")
    public List<String> getCollectionNames(Long datasourceId,String dbName) throws IOException {
        return IDatasourceQueryService.getCollectionNames(datasourceId,dbName);
    }

    /**
     * 获取PG table schema
     *
     * @param datasourceId
     * @return
     */
    @GetMapping("/getDBSchema")
    @ApiOperation("根据数据源id获取 db schema")
    public List<String> getTableSchema(Long datasourceId) {
        return IDatasourceQueryService.getTableSchema(datasourceId);
    }

    /**
     * 根据数据源id获取可用表名
     *
     * @param datasourceId
     * @return
     */
    @GetMapping("/getTables")
    @ApiOperation("根据数据源id获取可用表名")
    public List<String> getTableNames(Long datasourceId,String tableSchema) throws IOException {
        return IDatasourceQueryService.getTables(datasourceId,tableSchema);
    }

    /**
     * 根据数据源id和表名获取所有字段
     *
     * @param datasourceId 数据源id
     * @param tableName    表名
     * @return
     */
    @GetMapping("/getColumns")
    @ApiOperation("根据数据源id和表名获取所有字段")
    public List<String> getColumns(Long datasourceId, String tableName) throws IOException {
        return IDatasourceQueryService.getColumns(datasourceId, tableName);
    }

    /**
     * 根据数据源id和sql语句获取所有字段
     *
     * @param datasourceId 数据源id
     * @param querySql     表名
     * @return
     */
    @GetMapping("/getColumnsByQuerySql")
    @ApiOperation("根据数据源id和sql语句获取所有字段")
    public List<String> getColumnsByQuerySql(Long datasourceId, String querySql) throws SQLException {
        return IDatasourceQueryService.getColumnsByQuerySql(datasourceId, querySql);
    }
}
