package com.yyok.share.admin.logging.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yyok
 * @date 2019-5-22
 */
@Data
public class LogErrorDTO implements Serializable {

    private String coder;

    private String username;

    private String remark;

    private String method;

    private String params;

    private String browser;

    private String requestIp;

    private String address;

    private Timestamp createTime;
}
