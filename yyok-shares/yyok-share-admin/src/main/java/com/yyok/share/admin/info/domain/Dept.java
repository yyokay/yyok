package com.yyok.share.admin.info.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
@TableName("info_dept")
public class Dept extends Domain implements Serializable {

    /**
     * 名称
     */
    @NotBlank(message = "部门名称不能为空")
    private String name;

    /**
     * 上级部门
     */
    @NotNull(message = "上级部门不能为空")
    private String pcode;

    public void copy(Dept source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
