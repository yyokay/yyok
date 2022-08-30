package com.yyok.share.admin.info.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
@TableName("info_dict")
public class DictInfo extends Domain {

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String name;

    private String label;

    public void copy(DictInfo source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
