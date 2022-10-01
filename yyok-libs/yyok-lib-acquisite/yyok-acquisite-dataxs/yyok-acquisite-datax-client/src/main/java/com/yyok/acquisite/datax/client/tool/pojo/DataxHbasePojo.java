package com.yyok.acquisite.datax.client.tool.pojo;

import com.yyok.acquisite.datax.client.dto.Range;
import com.yyok.acquisite.datax.client.dto.VersionColumn;
import com.yyok.acquisite.datax.client.entity.JobDatasource;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DataxHbasePojo {

  private List<Map<String,Object>> columns;

  /**
   * 数据源信息
   */
  private JobDatasource jdbcDatasource;


  private String readerHbaseConfig;

  private String readerTable;

  private String readerMode;

  private String readerMaxVersion;

  private Range readerRange;

  private String writerHbaseConfig;

  private String writerTable;

  private String writerMode;

  private VersionColumn writerVersionColumn;

  private String writerRowkeyColumn;
}
