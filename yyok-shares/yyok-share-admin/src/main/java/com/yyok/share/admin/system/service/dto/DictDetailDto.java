package com.yyok.share.admin.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class DictDetailDto implements Serializable {

    /**
     * 字典详细
     */
    private String coder;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 排序
     */
    private int sorter;

    /**
     * 字典code
     */
    private String dictCode;

    /**
     * 创建日期
     */
    private Timestamp createTime;
}
