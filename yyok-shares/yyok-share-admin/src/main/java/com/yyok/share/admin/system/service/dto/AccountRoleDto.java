package com.yyok.share.admin.system.service.dto;

import com.yyok.share.admin.system.domain.AccountRole;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class AccountRoleDto implements Serializable {

    //授权  granted authority
    private Collection<AccountRole> accountRoles;

}
