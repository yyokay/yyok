package com.yyok.share.admin.logging.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yyok
 * @date 2019-5-22
 */
@Data
public class LogSmallDTO implements Serializable {

    private String remark;

    private String requestIp;

    private Long time;

    private String address;

    private String browser;

    private Timestamp createTime;
}
