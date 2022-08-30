package com.yyok.share.admin.system.domain;

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
@TableName("sys_account_avatar")
public class AccountAvatar extends Domain implements Serializable {

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

    public void copy(AccountAvatar source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
