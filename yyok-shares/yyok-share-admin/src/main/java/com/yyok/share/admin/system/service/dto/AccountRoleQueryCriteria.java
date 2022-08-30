package com.yyok.share.admin.system.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yyok.share.admin.info.domain.User;
import com.yyok.share.admin.info.service.dto.DeptSmallDto;
import com.yyok.share.admin.info.service.dto.JobSmallDto;
import com.yyok.share.admin.system.domain.AccountRole;
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
public class AccountRoleQueryCriteria implements Serializable {

    //授权  granted authority
    private Collection<AccountRoleDto> grantedAuthority;

}
