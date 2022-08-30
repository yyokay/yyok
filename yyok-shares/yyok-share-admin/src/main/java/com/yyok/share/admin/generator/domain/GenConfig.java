package com.yyok.share.admin.generator.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.yyok.share.framework.domain.Domain;

/**
 * 代码生成配置
 *
 * @author yyok
 * @date 2019-01-03
 */
@Data
@NoArgsConstructor
@TableName("sys_gen_config")
public class GenConfig extends Domain {

    /**
     * 表明
     **/
    private String tableName;
    /**
     * 接口名称
     **/
    private String apiAlias;
    /**
     * 包路径
     */
    private String pack;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 前端文件路径
     */
    private String path;
    /**
     * 前端文件路径
     */
    private String apiPath;
    /**
     * 作者
     */
    private String author;
    /**
     * 表前缀
     */
    private String prefix;
    /**
     * 是否覆盖
     */
    private Boolean cover;

    public GenConfig(String tableName) {
        this.cover = false;
        this.moduleName = "yyok-";
        this.tableName = tableName;
    }
}
