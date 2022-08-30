package com.yyok.share.admin.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yyok
 * @date 2020-05-16
 */
@Data
@TableName("sys_role_resource")
public class RoleResource extends Domain implements Serializable {

    /**
     * 资源Code
     */
    private String resourceCode;

    /**
     * 角色Code
     */
    private String roleCode;

    public void copy(RoleResource source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
