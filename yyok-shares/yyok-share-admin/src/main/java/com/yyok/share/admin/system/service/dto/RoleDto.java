package com.yyok.share.admin.system.service.dto;

import com.yyok.share.admin.system.domain.Resource;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class RoleDto implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据权限
     */
    private String dataScope;

    /**
     * 角色级别
     */
    private int level;

    private Set<Resource> resources;

    /**
     * 创建日期
     */
    private Timestamp createTime;

    /**
     * 功能权限
     */
    private String permission;
}
