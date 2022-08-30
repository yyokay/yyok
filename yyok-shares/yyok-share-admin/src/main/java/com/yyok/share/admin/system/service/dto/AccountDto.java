package com.yyok.share.admin.system.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yyok.share.admin.info.domain.User;
import com.yyok.share.admin.info.service.dto.DeptSmallDto;
import com.yyok.share.admin.info.service.dto.JobSmallDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class AccountDto implements Serializable {


    @ApiModelProperty(hidden = true)
    private String coder;

    private String username;

    private String nickName;

    private String sex;

    private String avatar;

    private String email;

    private String phone;

    private int enabled;

    @JsonIgnore
    private String password;

    private Timestamp lastPasswordResetTime;

    private User user;

    //身份验证; 认证；鉴定;
    private Authentication authentication;

    // 生成令牌
    private String token;

    //授权  granted authority
    private Collection<SimpleGrantedAuthority> authorities;

    @ApiModelProperty(hidden = true)
    private Set<RoleSmallDto> roles;

    @ApiModelProperty(hidden = true)
    private JobSmallDto job;

    private DeptSmallDto dept;

    private String deptCode;

    private Timestamp createTime;
}
