package com.yyok.share.admin.info.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
@TableName("sys_accout_avatar")
public class UserAvatar extends Domain implements Serializable {

    /**
     * 真实文件名
     */
    private String realName;

    /**
     * 路径
     */
    private String path;

    /**
     * 大小
     */
    private String size;


    public void copy(UserAvatar source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
