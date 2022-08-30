package com.yyok.share.admin.info.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.admin.system.domain.Dict;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
@TableName("dict_info_detail")
public class DictInfoDetail extends Domain {

    /**
     * 字典标签
     */
    private String label;


    /**
     * 字典值
     */
    private String value;

    /**
     * 排序
     */
    private int sorter;

    /**
     * 字典code
     */
    private String dictCode;

    @TableField(exist = false)
    private DictInfo dictInfo;

    public void copy(DictInfoDetail source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
