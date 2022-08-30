package com.yyok.share.admin.security.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yyok
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineAccount {

    private String userName;

    private String nickName;

    private String accountCode;

    private String job;

    private String browser;

    private String ip;

    private String address;

    private String key;

    private Date loginTime;

}
