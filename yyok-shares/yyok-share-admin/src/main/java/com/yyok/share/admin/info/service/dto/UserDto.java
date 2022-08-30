package com.yyok.share.admin.info.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class UserDto implements Serializable {

    @ApiModelProperty(hidden = true)
    private String coder;

    private String nickName;

    private String sex;

    private String avatar;

    private String email;

    private String phone;

    private int enabled;

    @ApiModelProperty(hidden = true)
    private JobSmallDto job;

    private DeptSmallDto dept;

    private String deptCode;

    private String jobCode;

    private String companyCode;

    private Timestamp createTime;
}
