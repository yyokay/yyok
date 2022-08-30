package com.yyok.share.admin.info.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
@TableName("info_job")
public class Job extends Domain implements Serializable {

    /**
     * 岗位名称
     */
    @NotBlank(message = "岗位名称不能为空")
    private String name;

    /**
     * 岗位状态
     */
    @TableField(exist = false)
    private Dept dept;

    /**
     * 部门Code
     */
    private String deptCode;


    public void copy(Job source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
