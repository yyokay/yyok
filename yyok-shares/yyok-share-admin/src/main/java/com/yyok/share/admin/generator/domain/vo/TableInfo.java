package com.yyok.share.admin.generator.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.yyok.share.framework.domain.Domain;

/**
 * 表的数据信息
 *
 * @author yyok
 * @date 2019-01-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo extends Domain {

    /**
     * 表名称
     */
    private Object tableName;


    /**
     * 数据库引擎
     */
    private Object engine;

    /**
     * 编码集
     */
    private Object coding;

    private String comment;


}
