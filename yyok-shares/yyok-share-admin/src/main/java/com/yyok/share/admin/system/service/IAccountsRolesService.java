package com.yyok.share.admin.system.service;

import com.yyok.share.admin.system.domain.AccountRole;
import com.yyok.share.admin.system.domain.vo.AccountRoleVo;
import com.yyok.share.framework.mapper.common.service.IBaseService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author yyok
 * @date 2020-05-16
 */
public interface IAccountsRolesService extends IBaseService<AccountRole> {
    public boolean grantAuthorization(List<AccountRoleVo> accountsRoles);

    HashMap verification(HashMap hs);

}
