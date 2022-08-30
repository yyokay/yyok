package com.yyok.share.admin.info.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class DeptDto implements Serializable {

    /**
     * ID
     */
    private String coder;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级部门
     */
    private String pcode;

    /**
     * 状态
     */
    private int enabled;

    private List<DeptDto> children;

    /**
     * 创建日期
     */
    private Timestamp createTime;

    public String getLabel() {
        return name;
    }
}
