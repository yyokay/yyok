package com.yyok.share.admin.system.domain;

import lombok.Data;

import java.util.HashMap;
/*
 */
@Data
public class AccountHashMapVo {

    /**
     * 账户Code
     */
    private String accountCode;

    /**
     * xxCode:form key is abac_code,acl_code,resouce_code;
     * form value is
     */
    private HashMap<String,String> xxCode;

}
