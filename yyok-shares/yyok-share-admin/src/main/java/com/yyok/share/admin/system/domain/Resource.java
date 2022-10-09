package com.yyok.share.admin.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
@TableName("sys_resource")
public class Resource extends Domain {


    /**
     * 是否外链
     */
    private Boolean iFrame;


    /**
     * 菜单名称
     */
    @NotBlank(message = "请填写菜单名称")
    private String name;


    /**
     * 组件
     */
    private String component;


    /**
     * 上级菜单ID
     */
    @NotNull(message = "上级菜单code不能为空")
    private String pcode;

    /**
     * 图标
     */
    private String icon;

    /**
     * 链接地址
     */
    private String path;

    /**
     * 缓存
     */
    private Boolean cache;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 组件名称
     */
    private String componentName;

    private String url;

    private String uri;

    private String method;

    /**
     * 权限
     */
    private String permission;

    /**
     * 类型，目录、菜单、按钮
     */


    public void copy(Resource source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
