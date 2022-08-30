package com.yyok.share.admin.security.security.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yyok.share.admin.info.domain.User;
import com.yyok.share.admin.system.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yyok
 * @date 2022-05-23
 */
@Getter
@Setter
@AllArgsConstructor
public class JwtAccount implements UserDetails {

    private final String coder;

    private final String account;

    private final String username;

    private final String nickName;

    private final String userCode;

    private final String avatar;

    private final String email;

    private final String phone;
    private final int enabled;
    private final String password;
    @JsonIgnore
    public final Collection<SimpleGrantedAuthority> authorities;

    private final User user;

    @JsonIgnore
    private final Date lastPasswordResetDate;

    private  String token;

    //身份验证; 认证；鉴定;
    @JsonIgnore
    private final Authentication authentication;

    private final Set<Role> roles;

    /*private final Set<Acl> acls;

    private final Set<Abac> abacs;*/
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return enabled== 1 ? true : false;
    }

    public Collection getAuthority() {
        return this.authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    public Set<Role> getRoles() {
        return roles;
    }
/*
    public Set<Acl> getAcls() {
        return acls;
    }

    //roles
    public Set<Abac> getAbacs() {
        return abacs;
    }*/

}
