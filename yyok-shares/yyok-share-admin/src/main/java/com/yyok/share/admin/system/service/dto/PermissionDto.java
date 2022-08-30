package com.yyok.share.admin.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author yyok
 * @date 2022-12-03
 */
@Data
public class PermissionDto implements Serializable {

    private String coder;

    private String name;

    private String pcode;

    private String alias;

    private Timestamp createTime;

    private List<PermissionDto> children;

    @Override
    public String toString() {
        return "Permission{" +
                "coder=" + coder +
                ", name='" + name + '\'' +
                ", pcode=" + pcode +
                ", alias='" + alias + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
