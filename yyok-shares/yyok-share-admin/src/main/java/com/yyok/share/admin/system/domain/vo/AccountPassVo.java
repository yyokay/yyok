package com.yyok.share.admin.system.domain.vo;

import lombok.Data;

/**
 * 修改密码的 Vo 类
 *
 * @author yyok
 */
@Data
public class AccountPassVo {

    private String oldPass;

    private String newPass;
}
