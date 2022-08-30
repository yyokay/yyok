package com.yyok.share.admin.security.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yyok.share.admin.system.domain.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author yyok
 * @date 2022-05-30
 */
@Getter
@Setter
public class AuthAccount {


    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";

    @JsonIgnore
    private String permission;
    @JsonIgnore
    private String[] dataScopes;
    @JsonIgnore
    private Set<Role> roleSet;
    @JsonIgnore
    private String token;


    @Override
    public String toString() {
        return "{username=" + username + ", password= ******}";
    }
}
