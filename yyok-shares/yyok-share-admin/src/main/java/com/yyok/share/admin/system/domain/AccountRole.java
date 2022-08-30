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
@TableName("sys_account_role")
public class AccountRole extends Domain implements Serializable {

    /**
     * 账户Code
     */
    private String accountCode;
    /**
     * 角色Code
     */
    private String roleCode;

    public void copy(AccountRole source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
