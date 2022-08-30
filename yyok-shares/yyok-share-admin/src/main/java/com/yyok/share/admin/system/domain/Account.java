package com.yyok.share.admin.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.admin.info.domain.User;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
@TableName("sys_account")
public class Account extends Domain {

    /**
     * 用户名
     */
    private String userCode;

    /**
     * 账户名
     */
    @NotBlank(message = "请填写账户")
    private String account;

    /**
     * 用户头像path
     */
    private String avatarPath;

    /**
     * 账户角色
     */
    @TableField(exist = false)
    private Set<Role> roles;

    /**
     * 用户
     */
    @TableField(exist = false)
    private User user;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 手机号码
     */
    @NotBlank(message = "请输入手机号码")
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 最后修改密码的日期
     */
    private Timestamp lastPasswordResetTime;

    //身份验证; 认证；鉴定;
    @TableField(exist = false)
    private Authentication authentication;

    // 生成令牌
    @TableField(exist = false)
    private String token;

    //授权  granted authority
    @TableField(exist = false)
    private Collection<SimpleGrantedAuthority> authorities;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(coder, account.coder) &&
                Objects.equals(phone, account.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coder, account);
    }

    public void copy(Account source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }

    public @interface Update {
    }
}
