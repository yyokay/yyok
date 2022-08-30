package com.yyok.share.admin.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
@TableName("sys_role")
public class Role extends Domain {

    /**
     * 名称
     */
    @NotBlank(message = "请填写角色名称")
    private String name;

    /**
     * 数据权限
     */
    private String dataScope;

    @TableField(exist = false)
    private Set<Resource> resources;

    @TableField(exist = false)
    private Set<DataScope> dataScopes;

    /**
     * 功能权限
     */
    private String permission;


    public void copy(Role source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
