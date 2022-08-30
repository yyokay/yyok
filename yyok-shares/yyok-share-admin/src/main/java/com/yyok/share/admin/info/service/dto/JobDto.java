package com.yyok.share.admin.info.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class JobDto implements Serializable {

    private String coder;

    private int sorter;

    private String name;

    private int enabled;

    private DeptDto dept;

    private String deptSuperiorName;

    private Timestamp createTime;

//    public JobDto(String name, Boolean enabled) {
//        this.name = name;
//        this.enabled = enabled;
//    }
}
